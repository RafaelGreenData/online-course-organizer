package com.oco.onlinecourseorganizer.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

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
}
