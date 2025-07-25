package com.oco.onlinecourseorganizer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CourseEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to the student (AppUser or custom StudentUser)
    @ManyToOne
    @JoinColumn(name = "student_id")
    private AppUser student;

    // Reference to the course being enrolled in
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDateTime enrollmentDate;

    public CourseEnrollment() {
        this.enrollmentDate = LocalDateTime.now(); // Set default enrollment time
    }

    public CourseEnrollment(AppUser student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDateTime.now();
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getStudent() {
        return student;
    }

    public void setStudent(AppUser student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
