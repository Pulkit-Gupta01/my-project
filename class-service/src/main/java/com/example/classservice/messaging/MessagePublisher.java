package com.example.classservice.messaging;

import com.example.classservice.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Method to send a message to the default class queue
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(RabbitConfig.CLASS_QUEUE, message);
    }

    // Method to send a message to a specific class queue based on class name
    public void sendMessageToClassQueue(String className, String message) {
        // Here you can construct the queue name if it follows a certain pattern
        String queueName = RabbitConfig.CLASS_QUEUE; // Use the class name to form a dynamic queue if needed
        rabbitTemplate.convertAndSend(queueName, message);
        // If you have specific queues for each class, you might want to use something like:
        // String queueName = className + "Queue";
        // Make sure to define the queue creation logic in your RabbitMQ setup
    }
}
