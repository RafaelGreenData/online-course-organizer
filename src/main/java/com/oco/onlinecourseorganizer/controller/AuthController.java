/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oco.onlinecourseorganizer.controller;

import com.oco.onlinecourseorganizer.dto.RegisterDTO;
import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.Role;
import com.oco.onlinecourseorganizer.repository.AppUserRepository;
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
    private AppUserRepository userRepository;
     
     @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "register";
    }
    
    // Handle form submission
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("registerDTO") RegisterDTO dto) {
        // Check if username already exists
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return "redirect:/register?error"; // email already used
        }

        AppUser newUser = new AppUser(
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                Role.STUDENT
        );

        userRepository.save(newUser);

        return "redirect:/login?registered";
    }
    
}
