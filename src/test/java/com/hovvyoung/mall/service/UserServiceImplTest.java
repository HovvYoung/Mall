package com.hovvyoung.mall.service;

import com.hovvyoung.mall.MallApplicationTests;
import com.hovvyoung.mall.enums.ResponseEnum;
import com.hovvyoung.mall.enums.RoleEnum;
import com.hovvyoung.mall.pojo.User;
import com.hovvyoung.mall.service.impl.UserServiceImpl;
import com.hovvyoung.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

// roll back
@Transactional
public class UserServiceImplTest extends MallApplicationTests {

    public static final String USERNAME = "tony";

    public static final String PASSWORD = "tony";

    public static final String EMAIL = "tony@gmail.com";

    @Autowired
    private UserServiceImpl userService;

    @Before
    public void register() {
        User user = new User(USERNAME, PASSWORD,EMAIL, RoleEnum.CUSTOM.getCode());
        userService.register(user);
    }

    @Test
    public void login() {
        ResponseVo<User> responseVo = userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}