package com.hovvyoung.mall;

import com.hovvyoung.mall.consts.MallConst;
import com.hovvyoung.mall.exception.UserLoginException;
import com.hovvyoung.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    /**
     * true: continue; false: intercept
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);

        if (user == null) {
            log.info("user=null");
            throw new UserLoginException();

            //return contont
//			response.getWriter().print("error");
//			return false;
//			return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }
        return true;
    }
}
