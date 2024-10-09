package com.example.classservice.repository;

import com.example.classservice.model.Class;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends MongoRepository<Class, String> {
    List<Class> findByTeacherId(String teacherId);
    List<Class> findByTeacherName(String teacherName);
    Optional<Class> findByName(String name);
}
