package com.testtask.noteservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    USER("User"),
    ADMIN("Admin");

    private final String name;
}
