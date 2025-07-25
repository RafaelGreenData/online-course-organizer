package com.oco.onlinecourseorganizer.service;

import com.oco.onlinecourseorganizer.model.CourseModule;
import com.oco.onlinecourseorganizer.repository.CourseModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseModuleService {

    private final CourseModuleRepository moduleRepository;

    public CourseModuleService(CourseModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<CourseModule> getModulesByCourseId(Long courseId) {
        return moduleRepository.findByCourseId(courseId);
    }

    public CourseModule getModuleById(Long id) {
        return moduleRepository.findById(id).orElse(null);
    }

    public void saveModule(CourseModule module) {
        moduleRepository.save(module);
    }

    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }
    
}
