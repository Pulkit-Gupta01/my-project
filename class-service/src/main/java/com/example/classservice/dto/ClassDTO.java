package com.example.classservice.dto;

import jakarta.validation.constraints.NotBlank;

public class ClassDTO {
    private String id;

    @NotBlank(message = "Class name cannot be empty")
    private String name;

    @NotBlank(message = "Teacher ID cannot be empty")
    private String teacherId;

    @NotBlank(message = "Teacher name cannot be empty")
    private String teacherName;

    // Constructor
    public ClassDTO() {}

    public ClassDTO(String id, String name, String teacherId, String teacherName) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
    }

    public String getId() {
        return id;
    }

    // Method to set ID (if needed)
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    // Optional: Override toString method for better logging
    @Override
    public String toString() {
        return "ClassDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
