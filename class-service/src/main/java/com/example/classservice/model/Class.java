package com.example.classservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "classes")
public class Class {

    @Id
    private String id;
    private String name;
    private String teacherId;
    private String teacherName;
    private Set<String> studentIds; // Assuming you store student IDs in a set

    public Class() {
        this.studentIds = new HashSet<>();
    }

    public Class(String id, String name, String teacherId, String teacherName) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.studentIds = new HashSet<>();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

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

    public Set<String> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(Set<String> studentIds) {
        this.studentIds = studentIds;
    }

    // Method to add a student ID to the class
    public void addStudent(String studentId) {
        this.studentIds.add(studentId);
    }
}
