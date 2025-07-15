package com.onlinecourseorganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // This method maps the root URL to the home page
    @GetMapping("/")
    public String showHomePage(Model model) {
        // You can add any data to the model if needed
        model.addAttribute("pageTitle", "Welcome to Online Course Organizer");

        // Return the name of the HTML template (home.html)
        return "home";
    }
}
