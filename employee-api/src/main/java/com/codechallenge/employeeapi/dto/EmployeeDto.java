package com.codechallenge.employeeapi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {

    private UUID id;

    @NotEmpty
    private String email;

    private String firstName;

    private String lastName;

    private Date birthday;

    private Set<String> hobbies;
}
