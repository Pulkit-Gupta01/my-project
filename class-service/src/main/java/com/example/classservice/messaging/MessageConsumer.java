package com.example.classservice.messaging;

import com.example.classservice.config.RabbitConfig;
import com.example.classservice.dto.StudentMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @RabbitListener(queues = RabbitConfig.CLASS_QUEUE)
    public void receiveStudentMessage(StudentMessageDTO studentMessage) {
        logger.info("Received message from classQueue: {}", studentMessage);

        processStudentMessage(studentMessage);
    }

    private void processStudentMessage(StudentMessageDTO studentMessage) {

        logger.info("Processing student message: {}", studentMessage);

    }
}
