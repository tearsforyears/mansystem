package com.example.demo.repositroy;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;


/**
 * @author zhanghaoyang
 */
@Scope("prototype")
@Data
@Repository
public class User implements Serializable {
    Long id;
    String userName;
    // 密码加盐由AOP技术实现
    String passWord;
    // 电话号码为账户
    String account;
    Boolean sex;
    // 数据库中设置默认修改就会更新这个字段
    Date lastLoginTime;
    Boolean enable = true;
    Date createTime;
    Date birthday;
}
