package com.example.student.controller;

import com.example.student.model.jpa.Student;
import com.example.student.model.mongo.StudentPreference;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @PostMapping("/preferences")
    public ResponseEntity<StudentPreference> createPreference(@RequestBody StudentPreference preference) {
        return new ResponseEntity<>(studentService.createPreference(preference), HttpStatus.CREATED);
    }

    @GetMapping("/preferences")
    public ResponseEntity<List<StudentPreference>> getAllPreferences() {
        return new ResponseEntity<>(studentService.getAllPreferences(), HttpStatus.OK);
    }
}
