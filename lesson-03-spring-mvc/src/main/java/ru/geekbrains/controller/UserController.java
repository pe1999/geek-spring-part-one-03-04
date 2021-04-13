package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

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
        User newUser = new User("NewUser");
        model.addAttribute("user", newUser);
        return "user_form";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userRepository.delete(id);
        return "redirect:/user";
    }

    @PostMapping
    public String submitForm(User user) {
        userRepository.save(user);
        return "redirect:/user";
    }
}
