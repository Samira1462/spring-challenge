package com.codechallenge.employeeapi.controller;

import com.codechallenge.employeeapi.dto.EmployeeDto;
import com.codechallenge.employeeapi.exception.ObjectNotFoundException;
import com.codechallenge.employeeapi.model.entity.Employee;
import com.codechallenge.employeeapi.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.codechallenge.employeeapi.validation.uuid.UuidValidation.isValidUUID;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {
    @Autowired
    private final ModelMapper mapper;
    @Autowired
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
    ) throws ObjectNotFoundException {
        if (!isValidUUID(id)) {
            throw new IllegalArgumentException("Invalid UUID format");
        }
        return ResponseEntity.ok(
                employeeService.getEmployee(id)
                .stream()
                .map(this::convertToEmployeeDto).findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("employee not found for this id :: " + id))
        );
    }

    @GetMapping(path = "/getAll", produces = "application/json")
    public ResponseEntity<List<EmployeeDto>> getAllEmployee() {
        return  ResponseEntity
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

    @PostMapping("/add")
    public ResponseEntity<String> add(@Validated @RequestBody EmployeeDto employeeDto) {

        employeeService.add(convertToEmployee(employeeDto));
        return ResponseEntity.ok().body("saved employee");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Validated @RequestBody EmployeeDto employeeDto, @PathVariable("id") UUID id) throws ObjectNotFoundException {
        Employee update = employeeService.update(convertToEmployee(employeeDto), id);
        if (update == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("could not find employee");
        }
        return ResponseEntity.ok(update);
    }

    protected EmployeeDto convertToEmployeeDto(Employee employee) {
        return mapper.map(employee, EmployeeDto.class);
    }

    protected Employee convertToEmployee(EmployeeDto employeeDto) {
        return mapper.map(employeeDto, Employee.class);
    }
}
