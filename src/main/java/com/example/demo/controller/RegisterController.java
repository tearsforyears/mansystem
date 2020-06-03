package com.example.demo.controller;

import com.example.demo.repositroy.User;
import com.example.demo.service.MessageService;
import com.example.demo.service.UserService;
import com.example.demo.utils.JsonReponseBuilder;
import com.example.demo.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author zhanghaoyang
 */
@RestController
public class RegisterController {

    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @PostMapping("/sendMessage")
    String sendMessage(String phone) {
        messageService.sendMessage(phone, "sys_register_code_");
        // send message to client
        return new JsonReponseBuilder().addItems(
                "state", "200",
                "msg", "message had been send"
        ).build();
    }

    @PostMapping("/sendLoginMessage")
    String sendLoginMessage(String account) {
        messageService.sendMessage(account, "sys_login_code_");
        // send message to client
        return new JsonReponseBuilder().addItems(
                "state", "200",
                "msg", "message had been send"
        ).build();
    }

    @PostMapping("/register")
    String register(String phone, String username, String password, Boolean sex, Date birthday, String code) {
        // user注入属性
        User user = SpringContextUtil.getBean(User.class);
        user.setAccount(phone);
        user.setUserName(username);
        user.setPassWord(password);
        user.setSex(sex);
        user.setBirthday(birthday);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        user.setCreateTime(new Date());
        user.setLastLoginTime(new Date());
        String message = userService.register(user, code);
        return new JsonReponseBuilder().addItems(
                "state", "200",
                "msg", message
        ).build();
    }

}
