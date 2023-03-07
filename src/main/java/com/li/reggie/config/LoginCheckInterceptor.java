package com.li.reggie.config;

import com.alibaba.fastjson.JSON;
import com.li.reggie.utils.Result;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    //路径匹配器，支持通配符
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取访问路径
        String requestURI = request.getRequestURI();

        //设置放行路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };

        //若访问路径匹配放行路径则放行
        for (String url : urls) {
            if (PATH_MATCHER.match(url, requestURI)) {
                return true;
            }
        }

        //若用户已登录则放行
        if (request.getSession().getAttribute("employee") != null) {
            return true;
        }

        //若上述情况都不符合说明不允许访问现访问资源，响应客户端数据
        response.getWriter().write(JSON.toJSONString(Result.err("NOTLOGIN")));

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
