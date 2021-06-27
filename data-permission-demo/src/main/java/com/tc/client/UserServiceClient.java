package com.tc.client;


import com.tc.domain.Department;
import com.tc.domain.User;

import java.util.Arrays;
import java.util.List;

public class UserServiceClient {


    public static User getUser() {
        User user = new User();
        user.setUserId(1);
        user.setDataScope(2);

        Department department = new Department();
        department.setId(1);
        department.setName("运营部");
        department.setSubDepartIds(Arrays.asList(3, 4, 5));
        user.setDepartment(department);

        user.setShopId(1);
        return user;
    }

}
