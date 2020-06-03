package com.example.demo.controller;

import com.example.demo.repositroy.Community;
import com.example.demo.repositroy.User;
import com.example.demo.service.CommunityService;
import com.example.demo.service.InOutService;
import com.example.demo.service.UserService;
import com.example.demo.utils.JsonReponseBuilder;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zhanghaoyang
 */
@RestController
public class InfoController {
    @Autowired
    UserService userService;
    @Autowired
    InOutService inOutService;
    @Autowired
    CommunityService communityService;


    @GetMapping("/info/getUser")
    public String getUserById(String token) {
        User user = userService.getUserByUserId(JwtUtil.getTokenValue(token, "uid"));
        return new JsonReponseBuilder().addItems(
                "account", user.getAccount(),
                "user_name", user.getUserName(),
                "sex", user.getSex().toString(),
                "birthday", user.getBirthday().toString()
        ).build();
    }

    /**
     * 返回用户管理的所有社区信息
     *
     * @param token
     * @return
     */
    @GetMapping("/info/getManComs")
    public String getManComs(String token) {
        return communityService.findManageCommunities(
                JwtUtil.getTokenValue(token, "uid"));
    }

    @GetMapping("/info/getManCom")
    public String getManCom(String token, String id) {
        return communityService.findManageCommunity(id);
    }

    @GetMapping("/info/getUserCommunity")
    public String getCommunity(String token) {
        return communityService.findCommunityByUserId(JwtUtil.getTokenValue(token, "uid"));
    }

    @GetMapping("/info/getCommunities")
    public String getAllCommunity(String page, String pagesize) {
        return communityService.findAll(Integer.parseInt(page), Integer.parseInt(pagesize));
    }

    @GetMapping("/info/countCommunities")
    public String count() {
        return new JsonReponseBuilder().addItems("amount", communityService.count().toString()).build();
    }

    @PostMapping("/info/join")
    public String joinCommunity(String token, String id) {
        String msg = communityService.joinCommunity(JwtUtil.getTokenValue(token, "uid"), id);
        return new JsonReponseBuilder().addItems(
                "msg", msg
        ).build();
    }

    @PostMapping("/info/insertCommunity")
    public String insertCommunity(String token, String name, String description, String address) {
        Community c = new Community();
        c.setAddress(address);
        c.setDescription(description);
        c.setName(name);
        return communityService.insertCommunity(c);
    }

    @PostMapping("/info/insertManCommunityUsers")
    public String insertManCommunityUsers(String token, String id) {
        return communityService.insertManCommunityUsers(JwtUtil.getTokenValue(token, "uid"), id);
    }

    @GetMapping("/info/inoutinfo")
    public String getInOutInfo(String token, String page, String pageSize) {
        return inOutService.getInOutInfo(JwtUtil.getTokenValue(token, "uid"), Integer.parseInt(page), Integer.parseInt(pageSize));
    }

    @GetMapping("/info/countinoutinfo")
    public String countInOutInfo(String token) {
        return inOutService.countInOutInfo(JwtUtil.getTokenValue(token, "uid"));
    }


}
