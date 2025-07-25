package com.oco.onlinecourseorganizer.service;

import com.oco.onlinecourseorganizer.model.AppUser;
import com.oco.onlinecourseorganizer.model.Course;
import com.oco.onlinecourseorganizer.model.CourseEnrollment;
import com.oco.onlinecourseorganizer.repository.CourseEnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final CourseEnrollmentRepository enrollmentRepository;

    public EnrollmentService(CourseEnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public void enrollStudentInCourse(AppUser student, Course course) {
        if (!enrollmentRepository.existsByStudentAndCourse(student, course)) {
            CourseEnrollment enrollment = new CourseEnrollment(student, course);
            enrollmentRepository.save(enrollment);
        }
    }

    public List<CourseEnrollment> getEnrolledCourses(AppUser student) {
        return enrollmentRepository.findByStudent(student);
    }

    public boolean isEnrolled(AppUser student, Course course) {
        return enrollmentRepository.existsByStudentAndCourse(student, course);
    }
    
    public void unenrollStudentFromCourse(AppUser student, Course course) {
        enrollmentRepository.findByStudentAndCourse(student, course)
                .ifPresent(enrollmentRepository::delete);
    }
}
