package com.example.classservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.classservice")
public class ClassServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClassServiceApplication.class, args);
    }
}
