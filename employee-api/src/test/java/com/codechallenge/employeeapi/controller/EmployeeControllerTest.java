package com.codechallenge.employeeapi.controller;

import com.codechallenge.employeeapi.exception.ObjectNotFoundException;
import com.codechallenge.employeeapi.model.entity.Employee;
import com.codechallenge.employeeapi.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService serviceUnderTest;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getEmployeeSuccessThenReturnSavedEmployee() throws Exception {
        // given
        UUID employeeId = UUID.fromString("39822545-e35d-4445-80a5-64336b59f166");
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("Samira");
        employee.setLastName("Radmaneshfar");
        employee.setEmail("Samira.Radmaneshfar@gamil.com");
        Date birthdayDate = new Date(1661617210633L);
        employee.setBirthday(birthdayDate);
        given(serviceUnderTest.getEmployee(employeeId)).willReturn(Optional.of(employee));

        // when
        ResultActions actual = mockMvc.perform(get("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        actual.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }


    @Test
    public void getEmployeeSuccessThenReturnNotFound() throws Exception {
        UUID employeeId = UUID.fromString("39822545-e35d-4445-80a5-64336b59f166");

        when(serviceUnderTest.getEmployee(employeeId)).thenThrow(new ObjectNotFoundException("employee not found for this id :: " + employeeId));

        mockMvc.perform(get("/api/employees/{id}", employeeId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("employee not found for this id :: " + employeeId));
    }

    @Test
    public void getEmployeeInvalidUUIDParameterThenReturnIs5xx() throws Exception {
        String invalidId = "not-a-valid-uuid";
        mockMvc.perform(get("/api/employees/{id}", invalidId))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getEmployeeJsonContentTypeThenReturnBadRequest() {

    }

    @Test
    public void getAllEmployeeReturnsOkResponseWithEmployeeList() throws Exception {
        // given
        UUID employeeId = UUID.fromString("39822545-e35d-4445-80a5-64336b59f166");
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("Samira");
        employee.setLastName("Radmaneshfar");
        employee.setEmail("Samira.Radmaneshfar@gamil.com");
        Date birthdayDate = new Date(1661617210633L);
        employee.setBirthday(birthdayDate);

        UUID employeeId2 = UUID.fromString("39822545-e35d-4445-80a5-64336b59f166");
        Employee employee2 = new Employee();
        employee2.setId(employeeId);
        employee2.setFirstName("Samira");
        employee2.setLastName("Radmaneshfar");
        employee2.setEmail("Samira.Rad@gamil.com");
        Date birthdayDate2 = new Date(1661617210633L);
        employee.setBirthday(birthdayDate2);

        List<Employee> mockEmployees = Arrays.asList(
                employee,
                employee2
        );

        when(serviceUnderTest.getAllEmployee()).thenReturn(mockEmployees);

        mockMvc.perform(get("/api/employees/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void GetAllEmployeeWhenNoEmployeesFoundThenReturnsEmptyList() {

    }

    @Test
    public void getAllEmployeeReturnsInternalServerErrorWhenServiceFails() {

    }
    @Test
    void deleteEmployeeWhenEmployeeIsDeletedThenReturnOk() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        doNothing().when(serviceUnderTest).deleteEmployee(id);

        // when
        mockMvc.perform(delete("/api/employees/{id}", id.toString())
                        .contentType("application/json"))
                .andExpect(status().isOk());

        // then
        verify(serviceUnderTest, times(1)).deleteEmployee(id);
    }

    @Test
    void deleteEmployeeWhenEmployeeIsNotFoundShouldReturnNotFoundResponse() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        doThrow(new ObjectNotFoundException("Employee not found")).when(serviceUnderTest).deleteEmployee(id);

        // when
        mockMvc.perform(delete("/api/employees/{id}", id.toString())
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        // then
        verify(serviceUnderTest, times(1)).deleteEmployee(id);
    }

    @Test
    void deleteEmployeeWhenInvalidIdFormatIsProvidedShouldReturnIs500() throws Exception {
        // then
        mockMvc.perform(delete("/api/employees/invalid-id")
                        .contentType("application/json"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void AddEmployeeThenReturnSuccess() {

    }

    @Test
    public void AddEmployeeThenReturnBadrequest() {

    }

    @Test
    void updateThenReturnSuccess() {
    }

    @Test
    void updateThenReturnEmployeeNotFound() {
    }

    @Test
    void updateWhenIdIsNullThenReturnException() {
    }

    @Test
    void updateWhenBodyIsNullThenReturnException() {
    }
}