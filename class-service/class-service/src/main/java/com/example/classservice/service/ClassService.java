package com.example.classservice.service;

import com.example.classservice.model.Class;
import com.example.classservice.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    // Method to get all classes
    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    // Method to get a class by ID
    public Optional<Class> getClassById(String id) {
        return classRepository.findById(id);
    }

    // Method to create a new class
    public Class createClass(Class newClass) {
        return classRepository.save(newClass);
    }

    // Method to update a class
    public Class updateClass(String id, Class updatedClass) {
        updatedClass.setId(id);
        return classRepository.save(updatedClass);
    }

    // Method to delete a class
    public void deleteClass(String id) {
        classRepository.deleteById(id);
    }
}
