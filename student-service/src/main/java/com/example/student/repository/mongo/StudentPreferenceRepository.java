package com.example.student.repository.mongo;

import com.example.student.model.mongo.StudentPreference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentPreferenceRepository extends MongoRepository<StudentPreference, String> {
}
