package com.example.demo.controller;

import com.example.demo.mapper.UserMapper;
import com.example.demo.repositroy.Community;
import com.example.demo.repositroy.User;
import com.example.demo.service.CommunityService;
import com.example.demo.service.UserService;
import com.example.demo.utils.JsonReponseBuilder;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhanghaoyang
 */
@RestController
public class LoginController {
    @Autowired
    UserService userService;

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    @GetMapping("/checkLogin")
    String isLogin(String token) {
        if (JwtUtil.verity(token)) {
            // token可用
            return new JsonReponseBuilder().addItems(
                    "redirect", "0"
            ).build();
        } else {
            // token过时
            return new JsonReponseBuilder().addItems(
                    "redirect", "1"
            ).build();
        }
    }

    /**
     * 调用登录服务
     * @param request
     * @param account
     * @param password
     * @param code
     * @return
     */
    @PostMapping("/login")
    String login(HttpServletRequest request, String account, String password, String code) {
        Map<String, String> info = userService.login(account, password, code);
        return new JsonReponseBuilder().addItems(
                "state", "200",
                "redirect", info.get("redirect"),
                "redirect_", info.get("redirect_"),
                "msg", info.get("msg"),
                "token", info.get("token")
        ).build();
    }
}

