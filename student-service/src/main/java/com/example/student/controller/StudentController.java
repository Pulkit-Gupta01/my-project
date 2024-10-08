package com.example.student.controller;

import com.example.student.messaging.MessagePublisher;
import com.example.student.model.jpa.Student;
import com.example.student.model.mongo.StudentPreference;
import com.example.student.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private MessagePublisher messagePublisher; // Inject MessagePublisher

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        logger.info("Request to create student: {}", student);
        try {

            String classId = "defaultClassId";
            String className = "defaultClassName";

            Student createdStudent = studentService.createStudent(student, classId, className);
            logger.info("Student successfully created: {}", createdStudent);


            String message = "New student created: ID = " + createdStudent.getId() + ", Name = " + createdStudent.getName();
            messagePublisher.sendMessage("classQueue", message); // Specify your queue name

            return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating student: {}", e.getMessage());
            return new ResponseEntity<>("Failed to create student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        logger.info("Request to fetch all students");
        try {
            List<Student> students = studentService.getAllStudents();
            logger.info("Number of students fetched: {}", students.size());
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching students: {}", e.getMessage());
            return new ResponseEntity<>("Failed to fetch students", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{studentId}/preferences")
    public ResponseEntity<?> createPreference(@PathVariable String studentId, @RequestBody StudentPreference preference) {
        logger.info("Request to create preference for studentId {}: {}", studentId, preference);
        try {
            StudentPreference createdPreference = studentService.createPreference(preference);
            logger.info("Preference successfully created for studentId {}: {}", studentId, createdPreference);
            return new ResponseEntity<>(createdPreference, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating preference for studentId {}: {}", studentId, e.getMessage());
            return new ResponseEntity<>("Failed to create preference", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{studentId}/preferences")
    public ResponseEntity<?> getPreferencesByStudentId(@PathVariable String studentId) {
        logger.info("Request to fetch preferences for studentId {}", studentId);
        try {
            List<StudentPreference> preferences = studentService.getPreferencesByStudentId(studentId);
            logger.info("Number of preferences fetched for studentId {}: {}", studentId, preferences.size());
            return new ResponseEntity<>(preferences, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching preferences for studentId {}: {}", studentId, e.getMessage());
            return new ResponseEntity<>("Failed to fetch preferences", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/preferences")
    public ResponseEntity<?> getAllPreferences() {
        logger.info("Request to fetch all preferences");
        try {
            List<StudentPreference> preferences = studentService.getAllPreferences();
            logger.info("Number of preferences fetched: {}", preferences.size());
            return new ResponseEntity<>(preferences, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching preferences: {}", e.getMessage());
            return new ResponseEntity<>("Failed to fetch preferences", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
