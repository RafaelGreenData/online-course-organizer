package com.onlinecourseorganizer.model;

import jakarta.persistence.*;

@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // === Constructors ===
    public Module() {}

    public Module(String title, String content, Course course) {
        this.title = title;
        this.content = content;
        this.course = course;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
