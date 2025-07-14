package com.onlinecourseorganizer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.onlinecourseorganizer.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Spring Data JPA gives you basic methods: save, findAll, findById, deleteById, etc.
}
