package com.aiportal.student_portal.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String title;

    @Column(length = 4000)
    private String description;

    private int credits;

    // Default constructor
    public Course() {
    }

    // Parameterized constructor
    public Course(String code, String title, String description, int credits) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.credits = credits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Course{"
                + "id=" + id
                + ", code='" + code + '\''
                + ", title='" + title + '\''
                + ", credits=" + credits
                + '}';
    }
}
