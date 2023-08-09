package com.codechallenge.employeeapi.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private UUID id;

    @NotEmpty
    private String email;

    private String firstName;

    private String lastName;

    private Date birthday;

    private Set<String> hobbies;
}
