package com.oco.onlinecourseorganizer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ModuleCompletion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The student who completed the module
    @ManyToOne
    private AppUser student;

    // The module that was completed
    @ManyToOne
    private CourseModule module;


    public ModuleCompletion() {
    }

    public ModuleCompletion(AppUser student, CourseModule module) {
        this.student = student;
        this.module = module;
    }

    // Getters and Setters

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

    public CourseModule getModule() {
        return module;
    }

    public void setModule(CourseModule module) {
        this.module = module;
    }
    
}
