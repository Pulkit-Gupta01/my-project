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

    @JsonProperty("studentId")
    private String studentId;

    @JsonProperty("classId")
    private String classId;

    @JsonProperty("className")
    private String className;

    // Override toString for better logging
    @Override
    public String toString() {
        return "StudentMessageDTO{" +
                "studentId='" + studentId + '\'' +
                ", classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
