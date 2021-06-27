package com.tc.controller;

import com.tc.common.R;
import com.tc.domain.User;
import com.tc.validation.BaseValidation;
import com.tc.validation.Insert;
import com.tc.validation.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {


    @GetMapping("/get")
    public R<List<User>> getUser(@NotNull(message = "{page.NotNull.message}") Integer page, @NotNull Integer size) {
        return R.ok();
    }


    @PostMapping("/add")
    public R addUser(@RequestBody @Validated(value = {Insert.class, Default.class}) User user) {
        return R.ok();
    }


    @PutMapping("/edit")
    public R editUser(@RequestBody @Validated(value = {Update.class, Default.class}) User user) {
        return R.ok();
    }

    @PostMapping("/add/validByZhuiZhi")
    public R addUserValidByZhuiZhi(@RequestBody User user) {
        BaseValidation.throwBaseKnownExceptionIfEmpty(user.getName(), "name");
        BaseValidation.throwBaseKnownExceptionIfEmpty(user.getPhonenumber(), "phonenumber");
        BaseValidation.throwBaseKnownExceptionIfEmpty(user.getEmail(), "email");
        return R.ok();
    }
}
