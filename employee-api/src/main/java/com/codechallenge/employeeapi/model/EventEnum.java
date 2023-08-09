package com.codechallenge.employeeapi.model;

public enum EventEnum {
    CREATED("Created"),
    UPDATED("Updated"),
    DELETED("Deleted");

    private final String displayName;

    EventEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;

    }
}
