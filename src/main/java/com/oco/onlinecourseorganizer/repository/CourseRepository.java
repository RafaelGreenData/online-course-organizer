package com.oco.onlinecourseorganizer.repository;

import com.oco.onlinecourseorganizer.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
