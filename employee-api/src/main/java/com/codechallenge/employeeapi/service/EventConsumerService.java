package com.codechallenge.employeeapi.service;

import com.codechallenge.employeeapi.model.EmployeeMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerService {
    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void employeeEventListener(EmployeeMessage message) {
        System.out.println("Received payload: " + message.getEmployee().getEmail());
    }
}