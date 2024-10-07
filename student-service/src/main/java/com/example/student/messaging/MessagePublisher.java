package com.example.student.messaging;

import com.example.classservice.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        System.out.println("Publishing message to RabbitMQ: " + message);
        rabbitTemplate.convertAndSend(RabbitConfig.CLASS_QUEUE, message);
    }

}