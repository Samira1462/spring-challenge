package com.codechallenge.employeeapi.service;

import com.codechallenge.employeeapi.model.Employee;
import com.codechallenge.employeeapi.model.EmployeeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class EventPublisherServiceTest {

    @Mock
    private KafkaTemplate<String, Serializable> kafkaTemplate;

    private EventPublisherService eventPublisherService;

    @BeforeEach
    public void setup() {
        eventPublisherService = new EventPublisherService(kafkaTemplate);
    }
    @Test
    @DisplayName("send employee event")
    public void sendEmployeeEvent() {

        UUID employeeId = UUID.fromString("39822545-e35d-4445-80a5-64336b59f166");
        Date birthday = new Date(1661617210633L);
        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("Sara")
                .lastName("Radmaneshfar")
                .email("Samira.Radmaneshfar@gmail.com")
                .birthday(birthday)
                .hobbies(List.of())
                .build();
        EmployeeMessage message = new EmployeeMessage(employee, "create");

        eventPublisherService.sendEmployeeEvent(message);

        assertDoesNotThrow(() ->  eventPublisherService.sendEmployeeEvent(message));
    }

}