package com.oco.onlinecourseorganizer.model;

import jakarta.persistence.*;

@Entity
public class CourseModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private ModuleType type; // VIDEO or QUIZ

    @Column(length = 1000)
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "course_id") // FK
    private Course course;

    // Getters and setters omitted for brevity
    // Enum for type
    public enum ModuleType {
        VIDEO,
        QUIZ
    }

    // Constructor
    public CourseModule() {
    }

    public CourseModule(String title, String description, ModuleType type, String videoUrl, Course course) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.videoUrl = videoUrl;
        this.course = course;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ModuleType getType() {
        return type;
    }

    public void setType(ModuleType type) {
        this.type = type;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
}
