package com.onlinecourseorganizer.controller;

import com.onlinecourseorganizer.model.User;
import com.onlinecourseorganizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Show registration page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Thymeleaf page
    }

    // Handle registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
    user.setRole("ROLE_STUDENT"); // default role student
    userService.save(user);
    return "redirect:/login";
}

    // Show login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
