package com.example.classservice.messaging;

import com.example.classservice.dto.StudentMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {
    private static final Logger logger = LoggerFactory.getLogger(MessagePublisher.class);
    private final RabbitTemplate rabbitTemplate;


    public static final String CLASS_QUEUE = "classQueue";

    @Autowired
    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void sendStudentMessage(String queueName, StudentMessageDTO studentMessage) {
        logger.info("Sending message to queue: {}: {}", queueName, studentMessage);
        rabbitTemplate.convertAndSend(queueName, studentMessage);
    }
}
