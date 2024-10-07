package com.example.classservice.service;

import com.example.classservice.dto.ClassDTO;
import com.example.classservice.mapper.ClassMapper;
import com.example.classservice.model.Class;
import com.example.classservice.repository.ClassRepository;
import com.example.classservice.messaging.MessagePublisher;
import com.example.classservice.dto.StudentMessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassService {

    private static final Logger logger = LoggerFactory.getLogger(ClassService.class);

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private MessagePublisher messagePublisher;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Method to get all classes
    public List<ClassDTO> getAllClasses() {
        logger.info("Fetching all classes");
        List<ClassDTO> classes = classRepository.findAll().stream()
                .map(classMapper::toDto)
                .collect(Collectors.toList());
        logger.info("Classes found: {}", classes);
        return classes;
    }

    // Method to get a class by ID
    public Optional<ClassDTO> getClassById(String id) {
        logger.info("Fetching class with ID: {}", id);
        return classRepository.findById(id)
                .map(classMapper::toDto)
                .map(classDTO -> {
                    logger.info("Class found: {}", classDTO);
                    return classDTO;
                });
    }

    // Method to create a new class
    public ClassDTO createClass(ClassDTO newClassDto) {
        logger.info("Creating new class: {}", newClassDto);
        Class newClassEntity = classMapper.toEntity(newClassDto);
        Class createdClassEntity = classRepository.save(newClassEntity);
        logger.info("Class created successfully: {}", createdClassEntity);
        return classMapper.toDto(createdClassEntity);
    }

    // Method to add a student to a class by class name
    public ClassDTO addStudentToClassByName(String className, String studentId) {
        logger.info("Adding student with ID {} to class {}", studentId, className);
        Class existingClass = classRepository.findByName(className)
                .orElseThrow(() -> {
                    logger.error("Class not found with name: {}", className);
                    return new NoSuchElementException("Class not found with name: " + className);
                });

        existingClass.addStudent(studentId); // Ensure this method is implemented in the Class model

        // Prepare the message
        StudentMessageDTO message = new StudentMessageDTO();
        message.setStudentId(studentId);
        message.setClassId(existingClass.getId());

        // Send the message as JSON
        try {
            String messageJson = objectMapper.writeValueAsString(message);
            messagePublisher.sendMessage(messageJson);
            logger.info("Message sent successfully: {}", messageJson);
        } catch (JsonProcessingException e) {
            logger.error("Error processing message for student {}: {}", studentId, e.getMessage());
        }

        // Save the updated class and return the DTO
        Class savedClass = classRepository.save(existingClass);
        logger.info("Student added successfully to class: {}", savedClass);
        return classMapper.toDto(savedClass);
    }

    // Method to update a class
    public Optional<ClassDTO> updateClass(String id, ClassDTO updatedClassDto) {
        logger.info("Updating class with ID: {}", id);
        return classRepository.findById(id).map(existingClass -> {
            existingClass.setName(updatedClassDto.getName());
            existingClass.setTeacherId(updatedClassDto.getTeacherId());
            existingClass.setTeacherName(updatedClassDto.getTeacherName());
            Class savedClass = classRepository.save(existingClass);
            logger.info("Class updated successfully: {}", savedClass);
            return classMapper.toDto(savedClass);
        });
    }

    // Method to delete a class
    public void deleteClass(String id) {
        logger.info("Deleting class with ID: {}", id);
        classRepository.deleteById(id);
        logger.info("Class deleted successfully");
    }

    // Method to find classes by teacher ID
    public List<ClassDTO> findClassesByTeacherId(String teacherId) {
        logger.info("Finding classes by teacher ID: {}", teacherId);
        List<ClassDTO> classes = classRepository.findByTeacherId(teacherId).stream()
                .map(classMapper::toDto)
                .collect(Collectors.toList());
        logger.info("Classes found for teacher ID {}: {}", teacherId, classes);
        return classes;
    }

    // Method to find classes by teacher name
    public List<ClassDTO> findClassesByTeacherName(String teacherName) {
        logger.info("Finding classes by teacher name: {}", teacherName);
        List<ClassDTO> classes = classRepository.findByTeacherName(teacherName).stream()
                .map(classMapper::toDto)
                .collect(Collectors.toList());
        logger.info("Classes found for teacher name {}: {}", teacherName, classes);
        return classes;
    }

    // Method to find a class by name
    public Optional<ClassDTO> findClassByName(String className) {
        logger.info("Finding class with name: {}", className);
        return classRepository.findByName(className)
                .map(classMapper::toDto)
                .map(classDTO -> {
                    logger.info("Class found: {}", classDTO);
                    return classDTO;
                });
    }
}
