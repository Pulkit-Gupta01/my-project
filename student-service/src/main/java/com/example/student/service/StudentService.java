package com.example.student.service;

import com.example.student.model.jpa.Student;
import com.example.student.model.mongo.StudentPreference;
import com.example.student.repository.jpa.StudentRepository;
import com.example.student.repository.mongo.StudentPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentPreferenceRepository studentPreferenceRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public StudentPreference createPreference(StudentPreference preference) {
        return studentPreferenceRepository.save(preference);
    }

    public List<StudentPreference> getAllPreferences() {
        return studentPreferenceRepository.findAll();
    }
}
