/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.oco.onlinecourseorganizer.service;

import com.oco.onlinecourseorganizer.dto.RegisterDTO;
import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.Role;
import com.oco.onlinecourseorganizer.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author brafa
 */
@Service
public class AppUserService {
    
    @Autowired
    private AppUserRepository userRepository;

    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(AppUser user) {
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public AppUser getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElse(null);
    }
    
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    
    public void registerStudent(RegisterDTO dto) {
        AppUser student = new AppUser(dto.getEmail(), passwordEncoder.encode(dto.getPassword()), Role.STUDENT);
        userRepository.save(student);
    }
    
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
