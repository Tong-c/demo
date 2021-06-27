package com.tc.domain;

import com.tc.common.annotation.NameInclude;
import com.tc.validation.Insert;
import com.tc.validation.Update;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class User {


    @NotNull(groups = Update.class)
    private Integer id;

    @NotNull(groups = Insert.class, message = "{name.NotNull.message}")
    @NameInclude(groups = Insert.class, message = "{name.NameInclude.message}", value = "111")
    private String name;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Pattern(regexp = "^[1][3,4,5,6,7,8,9][0-9]{9}$")
    private String phonenumber;

    @Valid
    @NotNull
    private Department department;

    public User() {
    }

    public User(Integer id, String name, String email, String phonenumber, Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
