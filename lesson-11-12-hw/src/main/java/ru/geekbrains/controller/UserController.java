package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.NotFoundException;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.dto.FilterDto;
import ru.geekbrains.persist.RoleRepository;
import ru.geekbrains.service.UserService;

import javax.validation.Valid;

// http://localhost:8080/spring-mvc-app/user
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String userListPage(Model model, FilterDto filterDto) {
        model.addAttribute("users", userService.findWithFilterSpec(filterDto));
        return "user";
    }

    @GetMapping("/{id}")
    public String userForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id)
                .orElseThrow(NotFoundException::new));
        model.addAttribute("roles", roleRepository.findAll());
        return "user_form";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        model.addAttribute("roles", roleRepository.findAll());
        return "user_form";
    }

    @PostMapping
    public String submitForm(@Valid @ModelAttribute("user") UserDto user, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "user_form";
        }

        if (user.getAge() > 50) {
            result.rejectValue("age","", "You are too old");
            model.addAttribute("roles", roleRepository.findAll());
            return "user_form";
        }

        if (!user.getPassword().equals(user.getMatchingPassword())) {
            result.rejectValue("matchingPassword", "", "Password not match");
            model.addAttribute("roles", roleRepository.findAll());
            return "user_form";
        }

        userService.save(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/user";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView mav = new ModelAndView("not_found");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}
