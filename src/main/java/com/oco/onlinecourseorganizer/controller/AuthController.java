/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oco.onlinecourseorganizer.controller;

import com.oco.onlinecourseorganizer.dto.RegisterDTO;
import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.Role;
import com.oco.onlinecourseorganizer.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 *
 * @author brafa
 */
@Controller
public class AuthController {
   
    
    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
     
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("registerDTO", new RegisterDTO());
        }
        
        return "register";
    }
    
    // Handle form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("RegisterDTO") @Valid RegisterDTO userDto,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }

        if (appUserService.emailExists(userDto.getEmail())) {
            redirectAttributes.addAttribute("emailExists", "true");
            return "redirect:/register";
        }

        appUserService.registerStudent(userDto);
        return "redirect:/login?registered";
        
            
    }
    
    @GetMapping("/redirect")
    public String redirectAfterLogin(Authentication authentication) {
        AppUser user = (AppUser) authentication.getPrincipal(); // Cast to your custom UserDetails

        if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin/dashboard";
        } else if (user.getRole() == Role.STUDENT) {
            return "redirect:/student/dashboard";
        }

        return "redirect:/"; // Fallback
    }
    
}
