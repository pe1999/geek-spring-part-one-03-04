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
import ru.geekbrains.service.UserService;

import javax.validation.Valid;

// http://localhost:8080/spring-mvc-app/user
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userListPage(Model model,
                               @RequestParam(name = "usernameFilter", required = false) String usernameFilter) {
        if (usernameFilter == null || usernameFilter.isBlank()) {
            model.addAttribute("users", userService.findAll());
        } else {
            model.addAttribute("users", userService.findAllByUsername(usernameFilter));
        }
        return "user";
    }

    @GetMapping("/{id}")
    public String userForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("userDto", userService.findById(id)
                .orElseThrow(NotFoundException::new));
        return "user_form";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "user_form";
    }

    @PostMapping
    public String submitForm(@Valid UserDto userDto, BindingResult result) {

        if (result.hasErrors()) {
            System.out.println("qwqwqwq");
            return "user_form";
        }

        if (userDto.getAge() > 50) {
            result.rejectValue("age","", "You are too old");
            return "user_form";
        }

        if (!userDto.getPassword().equals(userDto.getMatchingPassword())) {
            result.rejectValue("matchingPassword", "", "Password not match");
            return "user_form";
        }

        if (userService.findByUsername(userDto.getUsername()) != null) {
            result.rejectValue("username", "", "Username is exist!");
            return "user_form";
        }

        userService.save(userDto);
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
