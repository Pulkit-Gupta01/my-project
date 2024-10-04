package com.example.classservice.repository;

import com.example.classservice.model.Class;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClassRepository extends MongoRepository<Class, String> {
//TODO try writing custom JPA query
}
