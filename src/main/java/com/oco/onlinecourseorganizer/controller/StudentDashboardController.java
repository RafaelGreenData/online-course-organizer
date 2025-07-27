package com.oco.onlinecourseorganizer.controller;

import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.Course;
import com.oco.onlinecourseorganizer.model.CourseEnrollment;
import com.oco.onlinecourseorganizer.model.CourseModule;
import com.oco.onlinecourseorganizer.service.AppUserService;
import com.oco.onlinecourseorganizer.service.CourseModuleService;
import com.oco.onlinecourseorganizer.service.EnrollmentService;
import com.oco.onlinecourseorganizer.service.ModuleCompletionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentDashboardController {

    private final AppUserService appUserService;
    private final EnrollmentService enrollmentService;
    private final CourseModuleService courseModuleService;
    private final ModuleCompletionService completionService;

    public StudentDashboardController(AppUserService appUserService,
            EnrollmentService enrollmentService,
            CourseModuleService courseModuleService,
            ModuleCompletionService completionService) {
        this.appUserService = appUserService;
        this.enrollmentService = enrollmentService;
        this.courseModuleService = courseModuleService;
        this.completionService = completionService;
    }

    @GetMapping("/certificates")
    public String viewCertificates(Model model) {
        AppUser student = appUserService.getCurrentUser();
        List<CourseEnrollment> enrollments = enrollmentService.getEnrolledCourses(student);

        List<Course> completedCourses = enrollments.stream()
                .map(CourseEnrollment::getCourse)
                .filter(course -> {
                    List<CourseModule> modules = courseModuleService.getModulesByCourseId(course.getId());
                    if (modules.isEmpty()) {
                        return false;
                    }
                    long completed = modules.stream()
                            .filter(module -> completionService.isModuleCompleted(student, module))
                            .count();
                    return completed == modules.size();
                })
                .toList();

        model.addAttribute("completedCourses", completedCourses);
        return "student/my-certificates";
    }
}
