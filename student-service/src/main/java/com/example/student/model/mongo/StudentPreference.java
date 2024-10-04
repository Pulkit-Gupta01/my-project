package com.example.student.model.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "studentPreference")
public class StudentPreference {
    //TODO : how to change column names
    //TODO : how to set default values
    @Id
    private String id;
    private String studentId;
    private String preferences;
}
