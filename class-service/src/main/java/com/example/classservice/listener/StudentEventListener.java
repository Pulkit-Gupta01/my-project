package com.example.classservice.listener;

import com.example.classservice.dto.StudentMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventListener {

    private static final Logger logger = LoggerFactory.getLogger(StudentEventListener.class);

    @RabbitListener(queues = "classQueue")
    public void handleStudentEvent(StudentMessageDTO studentMessage) {
        // Logic to handle the student message
        logger.info("Received student message: studentId={}, classId={}",
                studentMessage.getStudentId(), studentMessage.getClassId());
        // Further processing logic here
    }
}
