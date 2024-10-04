package com.example.classservice.controller;

import com.example.classservice.model.Class;
import com.example.classservice.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClassController {

    @Autowired
    private ClassService classService;
    //TODO make this as github repo , push all the code there
    //TODO add proper endpoints for all the APIs
    //TODO try to use SLF4j logger
    //TODO get All should contain one request body which gives an option to give a filter value , based on which you will return the list
    //TODO explore pagination , how to return results if asked with pagination
    @GetMapping
    public ResponseEntity<List<Class>> getAllClasses() {
        //TODO add exception handling logics
        List<Class> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }


    @PostMapping
    public ResponseEntity<Class> createClass(@RequestBody Class newClass) {
        //TODO add validations in request body
        //TODO Use DTOs , use mapstruct mapper to map dto to entity
        Class createdClass = classService.createClass(newClass);
        return ResponseEntity.ok(createdClass);
    }
    //TODO find by Id , find By teacher Name
}
