package com.hovvyoung.mall.service.impl;

import com.hovvyoung.mall.dao.UserMapper;
import com.hovvyoung.mall.enums.ResponseEnum;
import com.hovvyoung.mall.enums.RoleEnum;
import com.hovvyoung.mall.pojo.User;
import com.hovvyoung.mall.service.IUserService;
import com.hovvyoung.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.hovvyoung.mall.enums.ResponseEnum.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo register(User user) {

        //check: user and email no duplicate
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0) {
            return ResponseVo.error(USERNAME_EXIST);
        }

        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
            return ResponseVo.error((EMAIL_EXIST));
        }

        user.setRole(RoleEnum.CUSTOM.getCode());
        // MD5 digest algorithm
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));

        // write to db
        int resultCount = userMapper.insertSelective((user));
        if (resultCount == 0) {
            return ResponseVo.error(ERROR);
        }

        return ResponseVo.success();
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            //user not exist（return : username or password error）
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        if (!user.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            //password error（return : username or password error）
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        user.setPassword("");   //when return to front end, clear the password.
        return ResponseVo.success(user);
    }
}
