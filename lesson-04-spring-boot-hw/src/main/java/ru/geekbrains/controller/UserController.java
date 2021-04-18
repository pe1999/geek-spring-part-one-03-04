package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.validation.Valid;

// http://localhost:8080/spring-mvc-app/user
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userListPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user";
    }

    @GetMapping("/{id}")
    public String userForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userRepository.findById(id));
        return "user_form";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user_form";
    }

    @PostMapping
    public String submitForm(@Valid User user, BindingResult result) {
        if (!user.getPassword().equals(user.getPasswordToConfirm())) {
            result.addError(new FieldError("user", "passwordToConfirm",
                    "Does not match the entered password"));
        }

        if (result.hasErrors()) {
            return "user_form";
        }

        userRepository.save(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable("id") long id) {
        userRepository.delete(id);
        return "redirect:/user";
    }
}
