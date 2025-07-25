package com.oco.onlinecourseorganizer.controller;

import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.Role;
import com.oco.onlinecourseorganizer.service.AppUserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    
    private final AppUserService appUserService;

    public DashboardController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Authentication authentication, Model model) {
        model.addAttribute("userEmail", authentication.getName());
        return "admin/dashboard";
    }

    @GetMapping("/student/dashboard")
    public String showStudentDashboard(Authentication authentication, Model model) {
        model.addAttribute("userEmail", authentication.getName());
        return "student/dashboard";
    }
    
    @GetMapping("/dashboard")
    public String redirectToDashboard() {
        AppUser user = appUserService.getCurrentUser();
        if (user.getRole() == Role.ADMIN) {
            return "redirect:/admin/dashboard";
        } else if (user.getRole() == Role.STUDENT) {
            return "redirect:/student/dashboard";
        }
        return "redirect:/login?error"; // fallback
    }
}
