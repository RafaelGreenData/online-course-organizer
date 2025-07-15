package com.onlinecourseorganizer.controller;

import com.onlinecourseorganizer.model.Course;
import com.onlinecourseorganizer.model.Module;
import com.onlinecourseorganizer.repo.CourseRepository;
import com.onlinecourseorganizer.repo.ModuleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/courses/{courseId}/modules")
public class ModuleController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    // Show the form to create a new module
    @GetMapping("/new")
    public String showCreateModuleForm(@PathVariable Long courseId, Model model) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));

        Module module = new Module();
        module.setCourse(course); // link course to module

        model.addAttribute("module", module);
        model.addAttribute("course", course);
        return "admin/create-module"; // refers to templates/admin/create-module.html
    }

    // Handle module creation
    @PostMapping
    public String saveModule(@PathVariable Long courseId, @ModelAttribute Module module) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));

        module.setCourse(course); // ensure link is set
        moduleRepository.save(module);

        return "redirect:/admin/courses";
    }
}
