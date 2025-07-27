package com.oco.onlinecourseorganizer.service;

import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.CourseModule;
import com.oco.onlinecourseorganizer.model.ModuleCompletion;
import com.oco.onlinecourseorganizer.repository.ModuleCompletionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModuleCompletionService {

    private final ModuleCompletionRepository repository;

    public ModuleCompletionService(ModuleCompletionRepository repository) {
        this.repository = repository;
    }

    // Check if completed
    public boolean isModuleCompleted(AppUser student, CourseModule module) {
        return repository.findByStudentAndModule(student, module).isPresent();
    }

    // Mark as completed
    public void markAsCompleted(AppUser student, CourseModule module) {
        if (!isModuleCompleted(student, module)) {
            ModuleCompletion completion = new ModuleCompletion(student, module);
            repository.save(completion);
        }
    }
}
