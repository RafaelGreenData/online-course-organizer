package com.onlinecourseorganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.onlinecourseorganizer.model.User;
import com.onlinecourseorganizer.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService; // Inject the service to save users

    // Show the registration page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // Pass empty user object to form
        return "register"; // Refers to templates/register.html
    }

    // Process the registration form
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute User user) {
        userService.save(user); // Save to database
        return "redirect:/login"; // After registration, redirect to login
    }

    // Show login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Refers to templates/login.html
    }
}
