package com.onlinecourseorganizer.model;

import jakarta.persistence.*;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private java.util.List<Module> modules = new java.util.ArrayList<>();

    // === Constructors ===
    public Course() {
    }

    public Course(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // === Getters and Setters ===
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public java.util.List<Module> getModules() {
        return modules;
    }

    public void setModules(java.util.List<Module> modules) {
        this.modules = modules;
    }
}
