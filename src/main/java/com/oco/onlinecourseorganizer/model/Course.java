package com.oco.onlinecourseorganizer.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;
    
    //@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<CourseModule> modules;
    

    // Constructors
    public Course() {}

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //public List<CourseModule> getModules() {
    //    return modules;
    //}

    //public void setModules(List<CourseModule> modules) {
    //    this.modules = modules;
    //}
}
