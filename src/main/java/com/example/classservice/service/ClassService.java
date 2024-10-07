package com.example.classservice.service;

import com.example.classservice.dto.ClassDTO;
import com.example.classservice.mapper.ClassMapper;
import com.example.classservice.model.Class;
import com.example.classservice.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ClassMapper classMapper;

    // Method to get all classes
    public List<ClassDTO> getAllClasses() {
        //TODO explore pagination, how to return results if asked with pagination
        return classRepository.findAll().stream()
                .map(classMapper::toDto)
                .collect(Collectors.toList());
    }

    // Method to get a class by ID
    public Optional<ClassDTO> getClassById(String id) {
        return classRepository.findById(id)
                .map(classMapper::toDto);
    }

    // Method to create a new class
    public ClassDTO createClass(ClassDTO newClassDto) {
        //TODO add validations in request body
        Class newClass = classMapper.toEntity(newClassDto);
        Class createdClass = classRepository.save(newClass);
        return classMapper.toDto(createdClass);
    }

    // Method to update a class
    public Optional<ClassDTO> updateClass(String id, ClassDTO updatedClassDto) {
        return classRepository.findById(id).map(existingClass -> {
            existingClass.setName(updatedClassDto.getName());
            existingClass.setTeacherId(updatedClassDto.getTeacherId());
            existingClass.setTeacherName(updatedClassDto.getTeacherName());
            Class savedClass = classRepository.save(existingClass);
            return classMapper.toDto(savedClass);
        });
    }

    // Method to delete a class
    public void deleteClass(String id) {
        classRepository.deleteById(id);
    }

    // Method to find classes by teacher ID
    public List<ClassDTO> findClassesByTeacherId(String teacherId) {
        return classRepository.findByTeacherId(teacherId).stream()
                .map(classMapper::toDto)
                .collect(Collectors.toList());
    }

    // Method to find classes by teacher name
    public List<ClassDTO> findClassesByTeacherName(String teacherName) {
        return classRepository.findByTeacherName(teacherName).stream()
                .map(classMapper::toDto)
                .collect(Collectors.toList());
    }
}
