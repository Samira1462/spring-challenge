package com.codechallenge.employeeapi.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeMessage implements Serializable {

    private String action;
    private Employee employee;

    private EmployeeMessage() {
    }

    public EmployeeMessage(Employee employee, String action) {
        this.employee = employee;
        this.action = action;
    }
}
