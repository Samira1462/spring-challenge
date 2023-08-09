package com.codechallenge.employeeapi.repository;

import com.codechallenge.employeeapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

}


