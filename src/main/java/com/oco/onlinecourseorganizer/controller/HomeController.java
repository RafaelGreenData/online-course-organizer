/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oco.onlinecourseorganizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author brafa
 */
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index"; // will map to src/main/resources/templates/index.html
    }
    
}
