/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oco.onlinecourseorganizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OnlineCourseOrganizerApplication {

    public static void main(String[] args) {
        
        //System.out.println("Admins password is:" + new BCryptPasswordEncoder().encode("admin123"));
        //System.out.println("✅ Entered /student/certificates controller method");
      
    
        SpringApplication.run(OnlineCourseOrganizerApplication.class, args);
    }
}