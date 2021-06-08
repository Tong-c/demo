package com.tc.domain;

import com.tc.common.annotation.NameInclude;
import com.tc.validation.Insert;
import com.tc.validation.Update;
import javax.validation.constraints.NotNull;


public class User {

    @NotNull(groups = Update.class)
    private Integer id;

    @NotNull(groups = Insert.class, message = "{name.NotNull.message}")
    @NameInclude(groups = Insert.class, message = "{name.NameInclude.message}", value = "111")
    private String name;

    public User() {
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
