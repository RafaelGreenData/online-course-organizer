package com.oco.onlinecourseorganizer.controller;

import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.Course;
import com.oco.onlinecourseorganizer.model.CourseEnrollment;
import com.oco.onlinecourseorganizer.model.CourseModule;
import com.oco.onlinecourseorganizer.service.AppUserService;
import com.oco.onlinecourseorganizer.service.CourseModuleService;
import com.oco.onlinecourseorganizer.service.CourseService;
import com.oco.onlinecourseorganizer.service.EnrollmentService;
import com.oco.onlinecourseorganizer.service.ModuleCompletionService;
import java.security.Principal;
import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student/courses")
public class StudentCourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final AppUserService appUserService;
    private final CourseModuleService courseModuleService;
    private final ModuleCompletionService moduleCompletionService;

    public StudentCourseController(CourseService courseService,
            EnrollmentService enrollmentService,
            AppUserService appUserService,
            CourseModuleService courseModuleService,
            ModuleCompletionService moduleCompletionService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.appUserService = appUserService;
        this.courseModuleService = courseModuleService;
        this.moduleCompletionService = moduleCompletionService;
    }

    // Show all courses the student has NOT yet enrolled in
    @GetMapping("/available")
    public String showAvailableCourses(Model model) {
        AppUser student = appUserService.getCurrentUser(); // Get logged-in user
        List<Course> allCourses = courseService.getAllCourses();
        List<Course> enrolledCourses = enrollmentService.getEnrolledCourses(student)
                .stream()
                .map(CourseEnrollment::getCourse)
                .toList();

        // Remove courses already enrolled
        allCourses.removeAll(enrolledCourses);

        model.addAttribute("courses", allCourses);
        return "student/available-courses";
    }

    // Enroll the student into a selected course
    @PostMapping("/{courseId}/enroll")
    public String enroll(@PathVariable Long courseId) {
        AppUser student = appUserService.getCurrentUser();
        Course course = courseService.getCourseById(courseId);
        enrollmentService.enrollStudentInCourse(student, course);
        return "redirect:/student/courses/my-courses";
    }

    // Show all courses the student has enrolled in
    @GetMapping("/my-courses")
    public String showMyCourses(Model model) {
        AppUser student = appUserService.getCurrentUser();
        List<CourseEnrollment> enrollments = enrollmentService.getEnrolledCourses(student);
        model.addAttribute("enrollments", enrollments);
        return "student/my-courses";
    }
    
    @PostMapping("/{courseId}/unenroll")
    public String unenroll(@PathVariable Long courseId) {
        AppUser student = appUserService.getCurrentUser();
        Course course = courseService.getCourseById(courseId);
        enrollmentService.unenrollStudentFromCourse(student, course);
        return "redirect:/student/courses/my-courses";
    }
    
    /*
    @GetMapping("/student/courses/{courseId}/modules")
    public String showCourseModules(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourseById(courseId); // existing method
        List<CourseModule> modules = courseModuleService.getModulesByCourseId(courseId);

        model.addAttribute("course", course);
        model.addAttribute("modules", modules);
        return "student/course-modules";
    }
    */
    
    @GetMapping("/{courseId}/modules")
    public String viewCourseModules(@PathVariable Long courseId, Model model) {
        AppUser student = appUserService.getCurrentUser(); // Get the logged-in student
        Course course = courseService.getCourseById(courseId);
        List<CourseModule> modules = courseModuleService.getModulesByCourseId(courseId);

        // Map of moduleId -> isCompleted
        Map<Long, Boolean> completedMap = new HashMap<>();
        for (CourseModule module : modules) {
            boolean isCompleted = moduleCompletionService.isModuleCompleted(student, module);
            completedMap.put(module.getId(), isCompleted);
        }

        model.addAttribute("course", course);
        model.addAttribute("modules", modules);
        model.addAttribute("completedMap", completedMap); // ✅ pass to Thymeleaf
        return "student/course-modules";
    }
    
    @PostMapping("/modules/{moduleId}/complete")
    public String markModuleAsCompleted(@PathVariable Long moduleId, Principal principal) {
        AppUser student = appUserService.findByEmail(principal.getName());
        CourseModule module = courseModuleService.getModuleById(moduleId);
        moduleCompletionService.markAsCompleted(student, module);
        return "redirect:/student/courses/modules/" + moduleId;
    }
    
    @GetMapping("/modules/{moduleId}")
    public String viewModule(@PathVariable Long moduleId, Model model, Principal principal) {
        CourseModule module = courseModuleService.getModuleById(moduleId);
        AppUser student = appUserService.getCurrentUser();

        boolean isCompleted = moduleCompletionService.isModuleCompleted(student, module);

        model.addAttribute("module", module);
        model.addAttribute("isCompleted", isCompleted);

        return "student/module-view"; // This will load module-view.html
    }

}
