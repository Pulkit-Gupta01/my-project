package com.example.classservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {
    private String id;
    private String name;
    private String teacherId;
    private String teacherName;
    private Set<String> studentIds;
}
