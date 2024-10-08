package com.example.student.service;

import com.example.student.dto.StudentMessageDTO;
import com.example.student.model.jpa.Student;
import com.example.student.model.mongo.StudentPreference;
import com.example.student.repository.jpa.StudentRepository;
import com.example.student.repository.mongo.StudentPreferenceRepository;
import com.example.student.messaging.MessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentPreferenceRepository studentPreferenceRepository;

    @Autowired
    private MessagePublisher messagePublisher; // Inject MessagePublisher

    public Student createStudent(Student student, String classId, String className) {
        logger.info("Creating student: {}", student);
        try {
            Student createdStudent = studentRepository.save(student);
            logger.info("Student created: {}", createdStudent);

            // Create a StudentMessageDTO
            StudentMessageDTO studentMessage = new StudentMessageDTO();
            studentMessage.setStudentId(String.valueOf(createdStudent.getId())); // Use the ID of the created student
            studentMessage.setClassId(classId);
            studentMessage.setClassName(className);

            // Send the message to the class service
            messagePublisher.sendStudentMessage("classQueue", studentMessage);
            logger.info("Sent student message to class service: {}", studentMessage);

            return createdStudent;
        } catch (Exception e) {
            logger.error("Error creating student: {}, Error: {}", student, e.getMessage());
            throw new RuntimeException("Error creating student");
        }
    }

    public List<Student> getAllStudents() {
        logger.info("Fetching all students");
        try {
            List<Student> students = studentRepository.findAll();
            logger.info("Number of students fetched: {}", students.size());
            return students;
        } catch (Exception e) {
            logger.error("Error fetching students: {}", e.getMessage());
            throw new RuntimeException("Error fetching students");
        }
    }

    public void addStudentToClass(String studentId, String classId) {
        // Implement logic to add the student to the class, e.g., update a Class entity
        logger.info("Adding student ID: {} to class ID: {}", studentId, classId);
        // Logic to update Class entity with the new student
    }

    public StudentPreference createPreference(StudentPreference preference) {
        logger.info("Creating preference: {}", preference);
        try {
            StudentPreference createdPreference = studentPreferenceRepository.save(preference);
            logger.info("Preference created: {}", createdPreference);
            return createdPreference;
        } catch (Exception e) {
            logger.error("Error creating preference: {}, Error: {}", preference, e.getMessage());
            throw new RuntimeException("Error creating preference");
        }
    }

    public List<StudentPreference> getAllPreferences() {
        logger.info("Fetching all preferences");
        try {
            List<StudentPreference> preferences = studentPreferenceRepository.findAll();
            logger.info("Number of preferences fetched: {}", preferences.size());
            return preferences;
        } catch (Exception e) {
            logger.error("Error fetching preferences: {}", e.getMessage());
            throw new RuntimeException("Error fetching preferences");
        }
    }

    public List<StudentPreference> getPreferencesByStudentId(String studentId) {
        logger.info("Fetching preferences for studentId {}", studentId);
        try {
            List<StudentPreference> preferences = studentPreferenceRepository.findByStudentId(studentId);
            logger.info("Number of preferences fetched for studentId {}: {}", studentId, preferences.size());
            return preferences;
        } catch (Exception e) {
            logger.error("Error fetching preferences for studentId {}: {}", studentId, e.getMessage());
            throw new RuntimeException("Error fetching preferences by student ID");
        }
    }
}
