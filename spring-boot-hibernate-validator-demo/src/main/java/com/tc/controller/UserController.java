package com.tc.controller;

import com.tc.common.R;
import com.tc.domain.User;
import com.tc.validation.Insert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {


    @GetMapping("/get")
    public R<List<User>> getUser(@NotNull(message = "{page.NotNull.message}") Integer page, Integer size) {
        List<User> result = new ArrayList<>();
        result.add(new User(1, "qdama"));
        return new R<>(200, "success", result);
    }


    @PostMapping("/add")
    public R addUser(@RequestBody @Validated(value = Insert.class) User user) {
        return new R(200, "success");
    }
}
