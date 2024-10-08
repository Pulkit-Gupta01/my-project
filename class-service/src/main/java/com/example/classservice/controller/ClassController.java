package com.example.classservice.controller;

import com.example.classservice.dto.ClassDTO;
import com.example.classservice.dto.StudentMessageDTO;
import com.example.classservice.messaging.MessagePublisher;
import com.example.classservice.service.ClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/classes")
public class ClassController {
    private static final Logger logger = LoggerFactory.getLogger(ClassController.class);

    @Autowired
    private ClassService classService;

    @Autowired
    private MessagePublisher messagePublisher; // Change to MessagePublisher

    @GetMapping
    public ResponseEntity<List<ClassDTO>> getAllClasses() {
        logger.info("Fetching all classes");
        try {
            List<ClassDTO> classes = classService.getAllClasses();
            return ResponseEntity.ok(classes);
        } catch (Exception e) {
            logger.error("Error fetching classes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDTO> getClassById(@PathVariable String id) {
        logger.info("Fetching class with ID: {}", id);
        try {
            return classService.getClassById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument provided for class ID: {}, Error: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error fetching class with ID: {}, Error: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ClassDTO>> getClassesByTeacherId(@PathVariable String teacherId) {
        logger.info("Fetching classes for teacherId: {}", teacherId);
        try {
            List<ClassDTO> classes = classService.findClassesByTeacherId(teacherId);
            return ResponseEntity.ok(classes);
        } catch (Exception e) {
            logger.error("Error fetching classes for teacherId: {}, Error: {}", teacherId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/teacherName/{teacherName}")
    public ResponseEntity<List<ClassDTO>> getClassesByTeacherName(@PathVariable String teacherName) {
        logger.info("Fetching classes for teacherName: {}", teacherName);
        try {
            List<ClassDTO> classes = classService.findClassesByTeacherName(teacherName);
            return ResponseEntity.ok(classes);
        } catch (Exception e) {
            logger.error("Error fetching classes for teacherName: {}, Error: {}", teacherName, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<ClassDTO> createClass(@RequestBody ClassDTO newClassDto) {
        logger.info("Creating a new class: {}", newClassDto.getName());
        try {
            ClassDTO createdClass = classService.createClass(newClassDto);
            return ResponseEntity.ok(createdClass);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument provided: {}, Error: {}", newClassDto, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error creating class: {}, Error: {}", newClassDto, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDTO> updateClass(@PathVariable String id, @RequestBody ClassDTO updatedClassDto) {
        logger.info("Updating class with ID: {}", id);
        try {
            return classService.updateClass(id, updatedClassDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument provided for class update with ID: {}, Error: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error updating class with ID: {}, Error: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable String id) {
        logger.info("Deleting class with ID: {}", id);
        try {
            classService.deleteClass(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            logger.error("Class with ID: {} not found, Error: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument provided for class deletion with ID: {}, Error: {}", id, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error deleting class with ID: {}, Error: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/name/{className}/students")
    public ResponseEntity<ClassDTO> addStudentToClassByClassName(@PathVariable String className, @RequestBody String studentId) {
        logger.info("Adding student with ID: {} to class with name: {}", studentId, className);
        try {
            if (studentId == null || studentId.isEmpty()) {
                logger.error("Invalid student ID provided: {}", studentId);
                return ResponseEntity.badRequest().build();
            }


            ClassDTO updatedClass = classService.addStudentToClassByName(className, studentId);


            StudentMessageDTO studentMessage = new StudentMessageDTO();
            studentMessage.setStudentId(studentId);
            studentMessage.setClassName(className);

            // Send a message to RabbitMQ
            messagePublisher.sendStudentMessage("classQueue", studentMessage);
            logger.info("Student message sent to RabbitMQ for student ID: {} in class: {}", studentId, className);

            return ResponseEntity.ok(updatedClass);
        } catch (NoSuchElementException e) {
            logger.error("Class with name: {} not found, Error: {}", className, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument provided for adding student with ID: {}, Error: {}", studentId, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error adding student with ID: {} to class with name: {}, Error: {}", studentId, className, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
