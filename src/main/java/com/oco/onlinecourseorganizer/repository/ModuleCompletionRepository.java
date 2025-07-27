package com.oco.onlinecourseorganizer.repository;

import com.oco.onlinecourseorganizer.model.ModuleCompletion;
import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuleCompletionRepository extends JpaRepository<ModuleCompletion, Long> {

    // Check if a student has completed a specific module
    Optional<ModuleCompletion> findByStudentAndModule(AppUser student, CourseModule module);
    boolean existsByStudentIdAndModuleId(Long studentId, Long moduleId);
}
