package com.codechallenge.employeeapi.controller;

import com.codechallenge.employeeapi.dto.EmployeeDto;
import com.codechallenge.employeeapi.exception.ObjectNotFoundException;
import com.codechallenge.employeeapi.model.Employee;
import com.codechallenge.employeeapi.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@Validated
public class EmployeeController {
    private final ModelMapper mapper;
    private final EmployeeService employeeService;

    public EmployeeController(ModelMapper mapper, EmployeeService employeeService) {
        this.mapper = mapper;
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<EmployeeDto> getEmployee(
            @Valid @PathVariable("id")
            @NotNull
            UUID id
    ) {
        return ResponseEntity.ok(
                employeeService.getEmployee(id)
                        .stream()
                        .map(this::convertToEmployeeDto).findFirst().orElse(null)
        );
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.getAllEmployee().stream()
                        .map(this::convertToEmployeeDto)
                        .collect(Collectors.toList()));
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") UUID id) throws ObjectNotFoundException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().body("deleted employee :: " + id);
    }

    @PostMapping
    public ResponseEntity<String> add(@Valid @RequestBody EmployeeDto employeeDto) {
        try {
            employeeService.add(convertToEmployee(employeeDto));
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("employee  created successfully.");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email address already exists.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody EmployeeDto employeeDto,
            @PathVariable("id") UUID id) throws DataIntegrityViolationException {
        try {
            Employee update = employeeService.update(convertToEmployee(employeeDto), id);
            if (update == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("could not find employee");
            }
            return ResponseEntity.ok(update);
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email address already exists.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    protected EmployeeDto convertToEmployeeDto(Employee employee) {
        return mapper.map(employee, EmployeeDto.class);
    }

    protected Employee convertToEmployee(EmployeeDto employeeDto) {
        return mapper.map(employeeDto, Employee.class);
    }
}
