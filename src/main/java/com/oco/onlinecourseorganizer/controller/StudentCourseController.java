package com.oco.onlinecourseorganizer.controller;

import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.Course;
import com.oco.onlinecourseorganizer.model.CourseEnrollment;
import com.oco.onlinecourseorganizer.model.CourseModule;
import com.oco.onlinecourseorganizer.service.AppUserService;
import com.oco.onlinecourseorganizer.service.CertificateService;
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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/student/courses")
public class StudentCourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final AppUserService appUserService;
    private final CourseModuleService courseModuleService;
    private final ModuleCompletionService completionService;
    private final CertificateService certificateService;

    public StudentCourseController(
            CourseService courseService,
            EnrollmentService enrollmentService,
            AppUserService appUserService,
            CourseModuleService courseModuleService,
            ModuleCompletionService completionService,
            CertificateService certificateService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.appUserService = appUserService;
        this.courseModuleService = courseModuleService;
        this.completionService = completionService;
        this.certificateService = certificateService;
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

    @GetMapping("/my-courses")
    public String showMyCourses(Model model) {
        AppUser student = appUserService.getCurrentUser();  // Get the currently logged-in student

        List<CourseEnrollment> enrollments = enrollmentService.getEnrolledCourses(student);

        // Maps to hold progress and certificate eligibility
        Map<Long, Integer> courseProgressMap = new HashMap<>();
        Map<Long, Boolean> courseCompletionMap = new HashMap<>();

        for (CourseEnrollment enrollment : enrollments) {
            Course course = enrollment.getCourse();
            List<CourseModule> modules = courseModuleService.getModulesByCourseId(course.getId());

            int totalModules = modules.size();
            int completed = (int) modules.stream()
                    .filter(module -> completionService.isModuleCompleted(student, module))
                    .count();

            int progress = totalModules == 0 ? 0 : (int) ((completed * 100.0) / totalModules);

            courseProgressMap.put(course.getId(), progress);
            courseCompletionMap.put(course.getId(), completed == totalModules);
        }

        model.addAttribute("enrollments", enrollments);
        model.addAttribute("courseProgressMap", courseProgressMap);
        model.addAttribute("courseCompletionMap", courseCompletionMap);

        return "student/my-courses";  // This will render the my-courses.html template
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
            boolean isCompleted = completionService.isModuleCompleted(student, module);
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
        completionService.markAsCompleted(student, module);
        return "redirect:/student/courses/modules/" + moduleId;
    }
    
    @GetMapping("/modules/{moduleId}")
    public String viewModule(@PathVariable Long moduleId, Model model, Principal principal) {
        CourseModule module = courseModuleService.getModuleById(moduleId);
        AppUser student = appUserService.getCurrentUser();

        boolean isCompleted = completionService.isModuleCompleted(student, module);

        model.addAttribute("module", module);
        model.addAttribute("isCompleted", isCompleted);

        return "student/module-view"; // This will load module-view.html
    }
    
    @GetMapping("/{courseId}/certificate")
    public ResponseEntity<Resource> downloadCertificate(@PathVariable Long courseId, Principal principal) {
        AppUser student = appUserService.findByEmail(principal.getName());

        if (!completionService.isCourseCompletedByStudent(courseId, student.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ByteArrayResource certificate = certificateService.generateCertificate(courseId, student);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=certificate.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(certificate);
    }
}
