package com.oco.onlinecourseorganizer.service;

import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.CourseModule;
import com.oco.onlinecourseorganizer.model.ModuleCompletion;
import com.oco.onlinecourseorganizer.repository.CourseModuleRepository;
import com.oco.onlinecourseorganizer.repository.ModuleCompletionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleCompletionService {

    private final ModuleCompletionRepository repository;
    private final CourseModuleRepository moduleRepository;

    public ModuleCompletionService(ModuleCompletionRepository repository, CourseModuleRepository moduleRepository) {
        this.repository = repository;
        this.moduleRepository = moduleRepository;
    }

    // ✅ Check if a specific module is completed
    public boolean isModuleCompleted(AppUser student, CourseModule module) {
        return repository.findByStudentAndModule(student, module).isPresent();
    }

    // ✅ Mark a module as completed if not already
    public void markAsCompleted(AppUser student, CourseModule module) {
        if (!isModuleCompleted(student, module)) {
            ModuleCompletion completion = new ModuleCompletion(student, module);
            repository.save(completion);
        }
    }

    // ✅ Check if the student completed all modules in a course
    public boolean isCourseCompletedByStudent(Long courseId, Long studentId) {
        List<CourseModule> courseModules = moduleRepository.findByCourseId(courseId);

        if (courseModules.isEmpty()) {
            return false;
        }

        long completedCount = courseModules.stream()
                .filter(module -> repository.existsByStudentIdAndModuleId(studentId, module.getId()))
                .count();

        return completedCount == courseModules.size();
    }
}
