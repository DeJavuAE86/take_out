package com.li.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.li.reggie.domain.User;
import com.li.reggie.service.UserService;
import com.li.reggie.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送手机短信验证码
     *
     * @param user
     * @return
     */
//    @PostMapping("/sendMsg")
//    public Result<String> sendMsg(@RequestBody User user, HttpSession session) {
//
//        //获取手机号
//        String phone = user.getPhone();
//
//        if (true) {
//            session.setAttribute(phone, "123456");
//
//            return Result.success("手机验证码短信发送成功");
//        }
//
//        return Result.err("短信发送失败");
//    }
    @PostMapping("/login")
    public Result<User> login(HttpServletRequest request, @RequestBody User user) {

        //获取手机号
        String phone = user.getPhone();

        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if (true) {
            //如果能够比对成功，说明登录成功

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User newUser = userService.getOne(queryWrapper);
            if (newUser == null) {
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                newUser = new User();
                newUser.setPhone(phone);
                newUser.setStatus(1);
                userService.save(newUser);
            }
            request.getSession().setAttribute("user", newUser.getId());

            return Result.success(user);
        }

        return Result.err("登录失败");
    }
}
