package com.oco.onlinecourseorganizer.repository;

import com.oco.onlinecourseorganizer.model.CourseEnrollment;
import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollment, Long> {

    List<CourseEnrollment> findByStudent(AppUser student);

    boolean existsByStudentAndCourse(AppUser student, Course course);

    Optional<CourseEnrollment> findByStudentAndCourse(AppUser student, Course course);
}
