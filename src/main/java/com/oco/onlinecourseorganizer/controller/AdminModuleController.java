package com.oco.onlinecourseorganizer.controller;

import com.oco.onlinecourseorganizer.model.Course;
import com.oco.onlinecourseorganizer.model.CourseModule;
import com.oco.onlinecourseorganizer.service.CourseModuleService;
import com.oco.onlinecourseorganizer.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/courses/{courseId}/modules")
public class AdminModuleController {

    private final CourseModuleService moduleService;
    private final CourseService courseService;

    public AdminModuleController(CourseModuleService moduleService, CourseService courseService) {
        this.moduleService = moduleService;
        this.courseService = courseService;
    }

    @GetMapping
    public String listModules(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourseById(courseId);
        List<CourseModule> modules = moduleService.getModulesByCourseId(courseId);
        model.addAttribute("course", course);
        model.addAttribute("modules", modules);
        return "admin/module-list";
    }

    @GetMapping("/create")
    public String showModuleForm(@PathVariable Long courseId, Model model) {
        CourseModule module = new CourseModule();
        module.setCourse(courseService.getCourseById(courseId));
        model.addAttribute("module", module);
        model.addAttribute("types", CourseModule.ModuleType.values());
        return "admin/module-form";
    }

    @PostMapping("/save")
    public String saveModule(@PathVariable Long courseId, @ModelAttribute CourseModule module, Model model) {
        if (module.getType() == CourseModule.ModuleType.VIDEO && (module.getVideoUrl() == null || module.getVideoUrl().isEmpty())) {
            model.addAttribute("module", module);
            model.addAttribute("types", CourseModule.ModuleType.values());
            model.addAttribute("error", "Video URL is required for VIDEO modules.");
            return "admin/module-form";
        }

        module.setCourse(courseService.getCourseById(courseId));
        moduleService.saveModule(module);
        return "redirect:/admin/courses/" + courseId + "/modules";
    }

    @GetMapping("/{moduleId}/delete")
    public String deleteModule(@PathVariable Long courseId, @PathVariable Long moduleId) {
        moduleService.deleteModule(moduleId);
        return "redirect:/admin/courses/" + courseId + "/modules";
    }
    
    @GetMapping("/{moduleId}")
    public String viewModule(@PathVariable Long courseId,
            @PathVariable Long moduleId,
            Model model) {
        CourseModule module = moduleService.getModuleById(moduleId);
        model.addAttribute("module", module);
        return "admin/module-view";
    }
}
