package com.codechallenge.employeeapi.controller;

import com.codechallenge.employeeapi.dto.EmployeeDto;
import com.codechallenge.employeeapi.exception.ObjectNotFoundException;
import com.codechallenge.employeeapi.model.Employee;
import com.codechallenge.employeeapi.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @DisplayName("get employee successful")
    public void getEmployeeSuccessThenReturnSavedEmployee() throws Exception {

        UUID employeeId = UUID.fromString("39822545-e35d-4445-80a5-64336b59f166");
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setFirstName("Samira");
        employee.setLastName("Radmaneshfar");
        employee.setEmail("Samira.Radmaneshfar@gamil.com");
        Date birthdayDate = new Date(1661617210633L);
        employee.setBirthday(birthdayDate);
        given(serviceUnderTest.getEmployee(employeeId)).willReturn(Optional.of(employee));

        ResultActions actual = mockMvc.perform(get("/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        actual.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    @DisplayName("get employee successful and return null")
    public void getEmployeeSuccessThenReturnNull() throws Exception {

        UUID employeeId = UUID.fromString("39822545-e35d-4445-80a5-64336b59f166");
        given(serviceUnderTest.getEmployee(employeeId)).willReturn(Optional.empty());

        ResultActions actual = mockMvc.perform(get("/employees/{id}", employeeId));

        actual.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("get employee with Invalid UUID then return client error")
    public void getEmployeeInvalidUUIDParameterThenReturnIs4xx() throws Exception {
        String invalidId = "not-a-valid-uuid";
        mockMvc.perform(get("/employees/{id}", invalidId))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("get all employee list")
    public void getAllEmployeeReturnsOkActualWithEmployeeList() throws Exception {

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
        employee2.setId(employeeId2);
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

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    @DisplayName("get all employee when no employees found then return empty List")
    public void getAllEmployeeWhenNoEmployeesFoundThenReturnsEmptyList() throws Exception {
        List<Employee> mockEmployees = List.of();

        when(serviceUnderTest.getAllEmployee()).thenReturn(mockEmployees);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    @DisplayName("delete employee with employee email")
    void deleteEmployeeWhenEmployeeIsDeletedThenReturnOk() throws Exception {

        UUID id = UUID.randomUUID();
        willDoNothing().given(serviceUnderTest).deleteEmployee(id);

        ResultActions actual = mockMvc.perform(delete("/employees/{id}", id.toString())
                        .contentType("application/json"))
                .andExpect(status().isOk());

        actual.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("delete employee is not found then return not found actual")
    void deleteEmployeeWhenEmployeeIsNotFoundShouldReturnNotFound() throws Exception {

        UUID id = UUID.randomUUID();
        doThrow(new ObjectNotFoundException("Employee not found")).when(serviceUnderTest).deleteEmployee(id);

        mockMvc.perform(delete("/employees/{id}", id.toString())
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

        verify(serviceUnderTest, times(1)).deleteEmployee(id);
    }

    @Test
    @DisplayName("delete employee is not found then return server error")
    void deleteEmployeeWhenInvalidIdFormatIsProvidedShouldReturnIs400() throws Exception {

        mockMvc.perform(delete("/employees/invalid-id")
                        .contentType("application/json"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("add employee successful")
    public void addEmployeeThenReturnSuccess() throws Exception {

        Date birthday = new Date(1661617210633L);
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName("Samira");
        dto.setLastName("Radmaneshfar");
        dto.setEmail("Samira.Radmaneshfar@gmail.com");
        dto.setBirthday(birthday);
        dto.setHobbies(Set.of("Reading, Swimming"));
        given(serviceUnderTest.add(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        ResultActions actual = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        actual.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("update employee successful")
    void updateThenReturnSuccess() throws Exception {

        UUID id = UUID.randomUUID();
        Date birthday = new Date(1661617210633L);
        Employee savedEmployee = Employee.builder()
                .firstName("Samira")
                .lastName("Radmaneshfar")
                .email("Samira.Radmaneshfar@gmail.com")
                .birthday(birthday)
                .hobbies("Reading, Swimming")
                .build();
        EmployeeDto updatedEmployee = new EmployeeDto();
        updatedEmployee.setFirstName("Samira");
        updatedEmployee.setLastName("Radmaneshfar");
        updatedEmployee.setEmail("Samira.Radmaneshfar@gmail.com");
        updatedEmployee.setBirthday(birthday);
        updatedEmployee.setHobbies(Set.of("Reading, Swimming"));

        given(serviceUnderTest.getEmployee(any(UUID.class)))
                .willReturn(Optional.ofNullable(savedEmployee));
        given(serviceUnderTest.update(any(Employee.class), any(UUID.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        //when
        ResultActions actual = mockMvc.perform(put("/employees/{id}", id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then

        actual.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())));
    }
}