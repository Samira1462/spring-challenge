package com.codechallenge.employeeapi.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class EmployeeDto {
    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "Invalid UUID format")
    private UUID id;

    @NonNull
    private String email;

    private String firstName;

    private String lastName;

    private Date birthday;

    private List<String> hobbies;
}
