package com.example.classservice.listener;

import com.example.classservice.dto.StudentMessageDTO;
import com.example.classservice.messaging.MessagePublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventListener {

    @Autowired
    private MessagePublisher messagePublisher;

    @EventListener
    public void handleStudentEvent(StudentMessageDTO message) {
        try {
            String messageJson = new ObjectMapper().writeValueAsString(message);
            messagePublisher.sendMessage(messageJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Log the error or handle accordingly
        }
    }
}
