package com.example.student.messaging;

import com.example.student.dto.StudentMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {
    private static final Logger logger = LoggerFactory.getLogger(MessagePublisher.class);
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Send a simple string message
    public void sendMessage(String queueName, String message) {
        logger.info("Sending message to queue: {} - {}", queueName, message);
        try {
            rabbitTemplate.convertAndSend(queueName, message);
        } catch (Exception e) {
            logger.error("Failed to send message to queue: {} - Error: {}", queueName, e.getMessage());
        }
    }

    // Send a StudentMessageDTO as a message
    public void sendStudentMessage(String queueName, StudentMessageDTO studentMessage) {
        logger.info("Sending student message to queue: {} - {}", queueName, studentMessage);
        try {
            rabbitTemplate.convertAndSend(queueName, studentMessage);
        } catch (Exception e) {
            logger.error("Failed to send student message to queue: {} - Error: {}", queueName, e.getMessage());
        }
    }
}
