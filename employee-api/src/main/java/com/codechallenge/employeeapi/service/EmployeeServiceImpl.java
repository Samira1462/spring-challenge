package com.codechallenge.employeeapi.service;

import com.codechallenge.employeeapi.exception.ObjectNotFoundException;

import com.codechallenge.employeeapi.model.entity.Employee;
import com.codechallenge.employeeapi.model.entity.EmployeeMessage;
import com.codechallenge.employeeapi.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final EventPublisherService kafkaEventPublisherService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EventPublisherService kafkaEventPublisherService) {
        this.employeeRepository = employeeRepository;
        this.kafkaEventPublisherService = kafkaEventPublisherService;
    }

    @Override
    public Employee add(Employee employee) throws DataIntegrityViolationException{
        Employee savedEmployee = employeeRepository.saveAndFlush(employee);
        kafkaEventPublisherService.sendEmployeeEvent(new EmployeeMessage(savedEmployee,"add"));
        return savedEmployee;
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
                .findByEmail(employee.getEmail())
                .orElseThrow(() -> new ObjectNotFoundException("employee not found for this id :: " + id));
        if (savedEmployee != null) {
            savedEmployee.setEmail(employee.getEmail());
            savedEmployee.setFirstName(employee.getFirstName());
            savedEmployee.setLastName(employee.getLastName());
            savedEmployee.setBirthday(employee.getBirthday());
            savedEmployee.setHobbies(employee.getHobbies());
            Employee employeeUpdated = employeeRepository.save(savedEmployee);
            kafkaEventPublisherService.sendEmployeeEvent(new EmployeeMessage(employeeUpdated,"edit"));
            return employeeUpdated;
        }
        return null;
    }

    @Override
    public void deleteEmployee(UUID id) throws ObjectNotFoundException {
        Employee existingEmployee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("employee not found for this id :: " + id));
        if (existingEmployee != null) {
            employeeRepository.delete(existingEmployee);
            kafkaEventPublisherService.sendEmployeeEvent(new EmployeeMessage(existingEmployee,"delete"));
        }
    }
}
