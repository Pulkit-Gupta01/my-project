// Package: com.example.classservice.dto

package com.example.classservice.dto;

public class ClassDTO {
    private String id;
    private String name;
    private String teacherId;
    private String teacherName;

    // Getters and Setters
    // You can add validation annotations here if needed
    public String getId() {
        return id;
    }

    //TODO add validations in DTO fields if necessary

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
}
