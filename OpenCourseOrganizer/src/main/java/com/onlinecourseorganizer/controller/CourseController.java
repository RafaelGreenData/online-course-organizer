package com.onlinecourseorganizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.onlinecourseorganizer.model.Course;
import com.onlinecourseorganizer.repo.CourseRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    // Show list of all courses
    @GetMapping
    public String listCourses(Model model) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "admin/courses"; // templates/admin/courses.html
    }

    // Show course creation form
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        return "admin/create-course"; // templates/admin/create-course.html
    }

    // Handle form submission
    @PostMapping
    public String saveCourse(@ModelAttribute Course course) {
        courseRepository.save(course);
        return "redirect:/admin/courses";
    }
}
