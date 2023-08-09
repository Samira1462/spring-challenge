package com.codechallenge.employeeapi.service;

import com.codechallenge.employeeapi.exception.ObjectNotFoundException;
import com.codechallenge.employeeapi.model.Employee;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface EmployeeService {
    Employee add(Employee employee) throws DataIntegrityViolationException;

    List<Employee> getAllEmployee();

    Optional<Employee> getEmployee(UUID uuid);

    Employee update(Employee employee, UUID uuid) throws ObjectNotFoundException, DataIntegrityViolationException;

    void deleteEmployee(UUID uuid) throws ObjectNotFoundException;
}
