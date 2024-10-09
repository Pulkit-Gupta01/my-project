package com.example.student.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentMessageDTO {

    @JsonProperty("student_id")  // Using snake_case to match JSON structure
    private String studentId;

    @JsonProperty("class_id")  // Using snake_case to match JSON structure
    private String classId;

    @JsonProperty("class_name")  // Using snake_case to match JSON structure
    private String className;

    // Override toString for better logging
    @Override
    public String toString() {
        return "StudentMessageDTO{" +
                "student_id='" + studentId + '\'' +
                ", class_id='" + classId + '\'' +
                ", class_name='" + className + '\'' +
                '}';
    }
}
