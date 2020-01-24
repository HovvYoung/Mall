package com.hovvyoung.mall.controller;

import com.hovvyoung.mall.consts.MallConst;
import com.hovvyoung.mall.enums.ResponseEnum;
import com.hovvyoung.mall.form.UserLoginForm;
import com.hovvyoung.mall.form.UserRegisterForm;
import com.hovvyoung.mall.pojo.User;
import com.hovvyoung.mall.service.IUserService;
import com.hovvyoung.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

//    @PostMapping("/register")
//    public void register(@RequestParam(value = "username") String name) {
//        log.info("username={}", name);
//    }

    //json format body needs @RequestBody
    @PostMapping("/user/register")
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userRegisterForm) {

//        if (bindingResult.hasErrors()) {
//            log.info("params inconsistent for registration, {} {}",
//                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
//                    bindingResult.getFieldError().getDefaultMessage());
//            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
//        }

        User user = new User();
        // copy userRegisterForm obj to user obj
        BeanUtils.copyProperties(userRegisterForm, user);

        //dto?
        return userService.register(user);
    }

    // Another way to get session HttpServletRequest httpServletRequest.getSession();
    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  HttpSession session) {

//        if (bindingResult.hasErrors()) {
//            log.info("params inconsistent for login, {} {}",
//                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
//                    bindingResult.getFieldError().getDefaultMessage());
//            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
//        }

        ResponseVo<User> userResponseVo  = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        session.setAttribute(MallConst.CURRENT_USER, userResponseVo.getData());
        log.info("/login sessionId={}", session.getId());

        return userResponseVo;
    }

    //session save in memory，Optimization：token+redis
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session) {
        log.info("/user sessionId={}", session.getId());
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        if (user == null) {
            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }
        return ResponseVo.success(user);
    }


    /**
     * {@link TomcatServletWebServerFactory} method: getSessionTimeoutInMinutes
     */
    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession session) {
        log.info("/user/logout sessionId={}", session.getId());
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();
    }
}
