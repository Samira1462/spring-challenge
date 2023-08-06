package com.codechallenge.employeeapi.service;

import com.codechallenge.employeeapi.model.entity.Employee;
import com.codechallenge.employeeapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;
    @Test
    void add() {
        //given
        Employee employee = new Employee();
        employee.setEmail("sami.....");
        employee.setLastName("rad.....");

        Employee createdEmployee = new Employee();
        createdEmployee.setId(UUID.randomUUID());
        createdEmployee.setEmail("sami.....");
        employee.setLastName("rad.....");

        //when
        when(employeeRepository.saveAndFlush(employee)).thenReturn(createdEmployee);

        Employee result = employeeService.add(employee);

        //then
        assertNotNull(result);
        assertEquals(createdEmployee.getId(), result.getId());
        assertEquals(createdEmployee.getEmail(), result.getEmail());
    }

    @Test
    void getAllEmployee() {
    }

    @Test
    void getEmployee() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteEmployee() {
    }
}