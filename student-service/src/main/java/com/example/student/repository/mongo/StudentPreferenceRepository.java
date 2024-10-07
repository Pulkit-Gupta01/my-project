package com.example.student.repository.mongo;

import com.example.student.model.mongo.StudentPreference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentPreferenceRepository extends MongoRepository<StudentPreference, String> {
    List<StudentPreference> findByStudentId(String studentId);
}
