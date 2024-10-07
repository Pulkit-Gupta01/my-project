package com.example.classservice.controller;

import com.example.classservice.dto.ClassDTO;
import com.example.classservice.service.ClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// Import validation annotations if needed
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classes")
public class ClassController {
    private static final Logger logger = LoggerFactory.getLogger(ClassController.class);

    @Autowired
    private ClassService classService;

    //TODO try to use SLF4j logger
    //TODO get All should contain one request body which gives an option to give a filter value, based on which you will return the list
    //TODO explore pagination, how to return results if asked with pagination

    @GetMapping
    public ResponseEntity<List<ClassDTO>> getAllClasses() {
        //TODO add exception handling logics
        logger.info("Fetching all classes");
        List<ClassDTO> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDTO> getClassById(@PathVariable String id) {
        logger.info("Fetching class with ID: {}", id);
        return classService.getClassById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ClassDTO>> getClassesByTeacherId(@PathVariable String teacherId) {
        logger.info("Fetching classes for teacherId: {}", teacherId);
        List<ClassDTO> classes = classService.findClassesByTeacherId(teacherId);
        return ResponseEntity.ok(classes);
    }

    @GetMapping("/teacherName/{teacherName}")
    public ResponseEntity<List<ClassDTO>> getClassesByTeacherName(@PathVariable String teacherName) {
        logger.info("Fetching classes for teacherName: {}", teacherName);
        List<ClassDTO> classes = classService.findClassesByTeacherName(teacherName);
        return ResponseEntity.ok(classes);
    }

    @PostMapping
    public ResponseEntity<ClassDTO> createClass(@RequestBody ClassDTO newClassDto) {
        //TODO add validations in request body
        //TODO Use DTOs, use mapstruct mapper to map dto to entity
        logger.info("Creating a new class: {}", newClassDto.getName());
        ClassDTO createdClass = classService.createClass(newClassDto);
        return ResponseEntity.ok(createdClass);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDTO> updateClass(@PathVariable String id, @RequestBody ClassDTO updatedClassDto) {
        // TODO: add proper exception handling
        logger.info("Updating class with ID: {}", id);
        return classService.updateClass(id, updatedClassDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable String id) {
        // TODO: add proper exception handling
        logger.info("Deleting class with ID: {}", id);
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}
