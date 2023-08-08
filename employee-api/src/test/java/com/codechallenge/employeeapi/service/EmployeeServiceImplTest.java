package com.codechallenge.employeeapi.service;

import com.codechallenge.employeeapi.exception.ObjectNotFoundException;
import com.codechallenge.employeeapi.model.entity.Employee;
import com.codechallenge.employeeapi.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeServiceUnderTest;

    @Mock
    private EventPublisherService eventPublisherService;

    @Test
    @DisplayName("add employee")
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

        Employee result = employeeServiceUnderTest.add(employee);

        //then
        assertNotNull(result);
        assertEquals(createdEmployee.getId(), result.getId());
        assertEquals(createdEmployee.getEmail(), result.getEmail());
    }

    @Test
    @DisplayName("get All employee")
    void getAllEmployee() {
        // given
        UUID id = UUID.randomUUID();
        Date birthday = new Date(1661617210633L);
        Employee savedEmployeeOne = Employee.builder()
                .id(id)
                .firstName("Samira")
                .lastName("Radmaneshfar")
                .email("Samira.Radmaneshfar@gmail.com")
                .birthday(birthday)
                .hobbies(List.of())
                .build();
        Employee savedEmployeeTwo = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Samira")
                .lastName("Radmaneshfar")
                .email("Sa.Radmaneshfar@gmail.com")
                .birthday(birthday)
                .hobbies(List.of())
                .build();
        List<Employee> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(savedEmployeeOne);
        expectedEmployees.add(savedEmployeeTwo);
        employeeRepository.saveAndFlush(savedEmployeeOne);
        employeeRepository.saveAndFlush(savedEmployeeTwo);

        // when
        when(employeeRepository.findAll()).thenReturn(expectedEmployees);

        List<Employee> actual = employeeServiceUnderTest.getAllEmployee();

        //then
        assertEquals(expectedEmployees.size(), actual.size());
        assertEquals(expectedEmployees, actual);
    }


    @Test
    @DisplayName("get Employee")
    void getEmployee() {
        // given
        UUID employeeId = UUID.fromString("39822545-e35d-4445-80a5-64336b59f166");
        Date birthday = new Date(1661617210633L);
        Employee savedEmployeeOne = Employee.builder()
                .id(employeeId)
                .firstName("Samira")
                .lastName("Radmaneshfar")
                .email("Samira.Radmaneshfar@gmail.com")
                .birthday(birthday)
                .hobbies(List.of())
                .build();

        // when
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.ofNullable(savedEmployeeOne));

        Optional<Employee> actual = employeeServiceUnderTest.getEmployee(UUID.fromString("39822545-e35d-4445-80a5-64336b59f166"));

        //then
        assert savedEmployeeOne != null;
        assertEquals(savedEmployeeOne.getFirstName(), actual.get().getFirstName());
    }

    @Test
    @DisplayName("update Employee")
    void update() throws ObjectNotFoundException {
        UUID employeeId = UUID.fromString("39822545-e35d-4445-80a5-64336b59f166");
        Date birthday = new Date(1661617210633L);
        Employee existingEmployee = Employee.builder()
                .id(employeeId)
                .firstName("Samira")
                .lastName("Radmaneshfar")
                .email("Samira.Radmaneshfar@gmail.com")
                .birthday(birthday)
                .hobbies(List.of())
                .build();
        Employee updatedEmployee = Employee.builder()
                .id(employeeId)
                .firstName("Sara")
                .lastName("Radmaneshfar")
                .email("Samira.Radmaneshfar@gmail.com")
                .birthday(birthday)
                .hobbies(List.of())
                .build();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);

        // when
        Employee actual = employeeServiceUnderTest.update(updatedEmployee, employeeId);

        // then
        assertNotNull(actual);
        assertEquals(updatedEmployee, actual);
    }

    @Test
    @DisplayName("update Employee")
    void UpdateNonExistentEmployee() {
        // given
        UUID employeeId = UUID.fromString("39822545-e35d-4445-80a5-64336b59f166");
        Date birthday = new Date(1661617210633L);
        Employee updatedEmployee = Employee.builder()
                .id(employeeId)
                .firstName("Sara")
                .lastName("Radmaneshfar")
                .email("Samira.Radmaneshfar@gmail.com")
                .birthday(birthday)
                .hobbies(List.of())
                .build();

        //when
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> employeeServiceUnderTest.update(updatedEmployee, employeeId));
    }
    @Test
    @DisplayName("delete Employee")
    void deleteEmployee() throws ObjectNotFoundException {
        // given
        UUID employeeId = UUID.fromString("39822545-e35d-4445-80a5-64336b59f166");
        Employee existingEmployee = new Employee(/* provide necessary details */);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));

        // then
        assertDoesNotThrow(() -> employeeServiceUnderTest.deleteEmployee(employeeId));
        verify(employeeRepository, times(1)).delete(existingEmployee);
    }

    @Test
    @DisplayName("delete non existent Employee then return ObjectNotFoundException")
    void deleteNonExistentEmployee() {
        // given
        UUID employeeId = UUID.randomUUID();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // then
        assertThrows(ObjectNotFoundException.class, () -> employeeServiceUnderTest.deleteEmployee(employeeId));
    }
}