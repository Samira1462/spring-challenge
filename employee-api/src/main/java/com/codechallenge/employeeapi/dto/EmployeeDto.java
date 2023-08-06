package com.codechallenge.employeeapi.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
//    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", message = "Invalid UUID format")
    private UUID id;

    @NonNull
    private String email;

    private String firstName;

    private String lastName;

    private Date birthday;

    private List<String> hobbies;
}
