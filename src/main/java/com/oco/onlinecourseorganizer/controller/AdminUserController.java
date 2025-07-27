package com.oco.onlinecourseorganizer.controller;

import com.oco.onlinecourseorganizer.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminUserController {

    private final AppUserService userService;

    public AdminUserController(AppUserService userService) {
        this.userService = userService;
    }

    // Show all users
    @GetMapping("/admin/users")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/user-list";
    }
}
