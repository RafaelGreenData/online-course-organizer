/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oco.onlinecourseorganizer.controller;

import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.Role;
import com.oco.onlinecourseorganizer.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
/**
 *
 * @author brafa
 */
@Controller
public class AuthController {
    
    @Autowired
    private AppUserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") AppUser user) {
        user.setRole(Role.STUDENT); // Only STUDENT can register
        userService.saveUser(user); // Save with encrypted password
        return "redirect:/login";
    }

    @GetMapping("/redirect")
    public String redirectByRole() {
        AppUser currentUser = userService.getCurrentUser();

        if (currentUser.getRole() == Role.ADMIN) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/student/dashboard";
        }
    }
    
}
