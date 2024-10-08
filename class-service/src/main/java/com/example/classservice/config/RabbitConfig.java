package com.example.classservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    public static final String CLASS_QUEUE = "classQueue";
    public static final String STUDENT_QUEUE = "studentQueue";

    // Set up the RabbitTemplate with JSON message converter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        logger.info("RabbitTemplate configured with Jackson2JsonMessageConverter");
        return rabbitTemplate;
    }

    // Declare the queue for class events
    @Bean
    public Queue classQueue() {
        logger.info("Creating classQueue...");
        return new Queue(CLASS_QUEUE, true); // Durable queue
    }

    // Declare the queue for student events (if needed in the future)
    @Bean
    public Queue studentQueue() {
        logger.info("Creating studentQueue...");
        return new Queue(STUDENT_QUEUE, true); // Durable queue
    }
}
