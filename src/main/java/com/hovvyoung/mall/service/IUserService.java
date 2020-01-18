package com.hovvyoung.mall.service;

import com.hovvyoung.mall.pojo.User;
import com.hovvyoung.mall.vo.ResponseVo;

public interface IUserService {
    /**
     * sign up
     * @return
     */
    ResponseVo<User> register(User user);



    /**
     * sign in
     */
    ResponseVo<User> login(String username, String password);
}
