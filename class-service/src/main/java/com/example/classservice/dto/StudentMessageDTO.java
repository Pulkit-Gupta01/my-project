package com.example.classservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

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

    @Override
    public String toString() {
        return "StudentMessageDTO{" +
                "studentId='" + studentId + '\'' +
                ", classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
