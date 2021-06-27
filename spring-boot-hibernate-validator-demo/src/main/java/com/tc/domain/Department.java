package com.tc.domain;

import javax.validation.constraints.NotNull;

public class Department {

    @NotNull(message = "{name.NotNull.message}")
    private String name;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
