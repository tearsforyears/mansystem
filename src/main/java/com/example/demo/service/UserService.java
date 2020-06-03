package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repositroy.User;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanghaoyang
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 登录逻辑
     * 用户名密码登录和验证码登录
     *
     * @param account  账户
     * @param password 密码
     * @param code     验证码
     * @return <type>Map<String,String> hashmap<type/>一个信息类
     * map里面的字段包含:
     * redirect 是否重定向
     * redirect_ 重定向的前端路由
     * msg 操作信息
     */
    public Map<String, String> login(String account, String password, String code) {
        Map<String, String> info = new HashMap<String, String>();
        if (("".equals(password) || password == null) && ("".equals(code) || code == null)) {
            info.put("redirect", "0");
            info.put("redirect_", "");
            info.put("msg", "密码和验证码为空");
        } else {
            if (userMapper.existUserByPhone(account)) {
                // 如果用户还未注册过
                if (code == null || "".equals(code)) {
                    // 密码登录
                    User user = SpringContextUtil.getBean(User.class);
                    user.setAccount(account);
                    user.setPassWord(password);
                    user = userMapper.findUserByAccountAndPassword(user);
                    if (user != null) {
                        info.put("redirect", "1");
                        info.put("redirect_", "Info");
                        info.put("msg", "登录成功");

                        // 生成token
                        Map<String, String> tokenDict = new HashMap<>();
                        tokenDict.put("uid", user.getId().toString());
                        String token = JwtUtil.getJWTToken(tokenDict);
                        info.put("token", token);

                    } else {
                        info.put("redirect", "0");
                        info.put("redirect_", "");
                        info.put("msg", "密码错误");
                    }
                } else {
                    // 验证码登录
                    MessageService messageService = SpringContextUtil.getBean(MessageService.class);
                    String redisCode = messageService.getPhoneCodeInRedis(account, "sys_login_code_");
                    if (redisCode != null && redisCode.equals(code)) {
                        User user = userMapper.findUserByPhone(account);
                        info.put("redirect", "1");
                        info.put("redirect_", "Info");
                        info.put("msg", "登录成功");

                        // 生成token
                        Map<String, String> tokenDict = new HashMap<>();
                        tokenDict.put("uid", user.getId().toString());
                        String token = JwtUtil.getJWTToken(tokenDict);
                        info.put("token", token);

                    } else {
                        info.put("redirect", "0");
                        info.put("redirect_", "");
                        info.put("msg", "验证码错误");
                    }
                }
            } else {
                info.put("redirect", "1");
                info.put("redirect_", "Register");
                info.put("msg", "account not in database");
            }

        }
        return info;
    }

    /**
     * 注册逻辑主要是 判断验证码是否正确 手机号是否重复
     * 然后选择操作数据库与否
     *
     * @param user
     * @param code
     * @return
     */
    public String register(User user, String code) {
        String phone = user.getAccount();
        MessageService messageService = SpringContextUtil.getBean(MessageService.class);
        // 如果电话号不是空
        if (!("".equals(phone) && null == phone)) {
            String redisPhoneCode = messageService.getPhoneCodeInRedis(phone, "sys_register_code_");
            // 如果redis中存有验证码
            if (!("".equals(redisPhoneCode) && null == redisPhoneCode)) {
                // 且验证码与用户输入一致
                if (code.equals(redisPhoneCode)) {
                    // 检查手机号是否重复
                    if (!userMapper.existUserByPhone(phone)) {
                        // 注册用户
                        userMapper.insertUser(user);
                        return "注册成功";
                    } else {
                        return "手机号已经被用于注册";
                    }
                } else {
                    return "注册失败,请重新获取验证码";
                }
            } else {
                return "未请求验证码,请重新申请验证码";
            }
        } else {
            return "电话号错误";
        }
    }

    public User getUserByUserId(String id) {
        return userMapper.findUserById(new Long(id));
    }

    public Long getUserIdByPhone(String phone) {
        return userMapper.findUserIdByPhone(phone);
    }

}
