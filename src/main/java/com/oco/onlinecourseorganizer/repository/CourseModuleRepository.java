package com.oco.onlinecourseorganizer.repository;

import com.oco.onlinecourseorganizer.model.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {

    List<CourseModule> findByCourseId(Long courseId); // Fetch by course
}
