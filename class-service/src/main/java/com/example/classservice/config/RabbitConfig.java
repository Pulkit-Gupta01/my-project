package com.example.classservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String CLASS_QUEUE = "classQueue";
    public static final String STUDENT_QUEUE = "studentQueue";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    // Declare the classQueue
    @Bean
    public Queue classQueue() {
        System.out.println("Creating classQueue..."); // Log message
        return new Queue(CLASS_QUEUE, true); // durable queue
    }


    // Declare the studentQueue
    @Bean
    public Queue studentQueue() {
        System.out.println("Creating studentQueue..."); // Log message
        return new Queue(STUDENT_QUEUE, true); // durable queue
    }
}
