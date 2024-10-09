package com.example.classservice.service;

import com.example.classservice.config.RabbitConfig;
import com.example.classservice.dto.ClassDTO;
import com.example.classservice.mapper.ClassMapper;
import com.example.classservice.model.Class;
import com.example.classservice.repository.ClassRepository;
import com.example.classservice.dto.StudentMessageDTO;
import com.example.classservice.messaging.MessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

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


    public List<ClassDTO> getAllClasses() {
        logger.info("Fetching all classes");
        List<ClassDTO> classes = classRepository.findAll().stream()
                .map(classMapper::toDto)
                .collect(Collectors.toList());
        logger.info("Classes found: {}", classes);
        return classes;
    }


    public Optional<ClassDTO> getClassById(String id) {
        logger.info("Fetching class with ID: {}", id);
        return classRepository.findById(id)
                .map(classMapper::toDto)
                .map(classDTO -> {
                    logger.info("Class found: {}", classDTO);
                    return classDTO;
                });
    }


    public ClassDTO createClass(ClassDTO newClassDto) {
        logger.info("Creating new class: {}", newClassDto);
        Class newClassEntity = classMapper.toEntity(newClassDto);
        Class createdClassEntity = classRepository.save(newClassEntity);
        logger.info("Class created successfully: {}", createdClassEntity);
        return classMapper.toDto(createdClassEntity);
    }


    public Class addStudentToClassByName(String className, String studentId) {
        Class classEntity = classRepository.findByName(className)
                .orElseThrow(() -> new NoSuchElementException("Class not found"));


        classEntity.addStudent(studentId);


        return classRepository.save(classEntity);
    }


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

    @RabbitListener(queues = RabbitConfig.CLASS_QUEUE)
    public void handleStudentMessage(StudentMessageDTO message) {
        logger.info("Received student message: {}", message);

        String studentId = message.getStudentId();
        String classId = message.getClassId();

        try {
            // Fetch the class by ID
            Class existingClass = classRepository.findById(classId)
                    .orElseThrow(() -> {
                        logger.error("Class not found with ID: {}", classId);
                        return new NoSuchElementException("Class not found with ID: " + classId);
                    });

            if (existingClass.getStudentIds().contains(studentId)) {
                logger.warn("Student with ID {} is already enrolled in class ID {}", studentId, classId);
            } else {

                existingClass.addStudent(studentId);


                Class savedClass = classRepository.save(existingClass);
                logger.info("Student with ID {} added successfully to class ID {}: {}", studentId, classId, savedClass);
            }

        } catch (NoSuchElementException e) {
            logger.error("Error adding student to class: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while adding student to class: {}", e.getMessage(), e);
        }
    }

    public String getClassIdByName(String className) {
        logger.info("Retrieving class ID for class name: {}", className);
        Class classEntity = classRepository.findByName(className)
                .orElseThrow(() -> new NoSuchElementException("Class not found"));
        logger.info("Found class ID: {}", classEntity.getId());
        return classEntity.getId();
    }


}
