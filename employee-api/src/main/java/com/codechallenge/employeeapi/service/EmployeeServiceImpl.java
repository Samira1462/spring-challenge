package com.codechallenge.employeeapi.service;

import com.codechallenge.employeeapi.exception.ObjectNotFoundException;
import com.codechallenge.employeeapi.model.entity.Employee;
import com.codechallenge.employeeapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee add(Employee employee) throws DataIntegrityViolationException{
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployee(UUID id) {
        return employeeRepository.findById(id);
    }
    @Override
    public Employee update(Employee employee, UUID id) throws ObjectNotFoundException {
        Employee savedEmployee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("employee not found for this id :: " + id));
        if (savedEmployee != null) {
            savedEmployee.setEmail(employee.getEmail());
            savedEmployee.setFirstName(employee.getFirstName());
            savedEmployee.setLastName(employee.getLastName());
            savedEmployee.setBirthday(employee.getBirthday());
            savedEmployee.setHobbies(employee.getHobbies());
            return employeeRepository.save(savedEmployee);
        }
        return null;
    }

    @Override
    public boolean deleteEmployee(UUID id) throws ObjectNotFoundException {
        Employee existingEmployee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("employee not found for this id :: " + id));
        if (existingEmployee != null) {
            employeeRepository.delete(existingEmployee);
            return true;
        }
        return false;
    }
}
