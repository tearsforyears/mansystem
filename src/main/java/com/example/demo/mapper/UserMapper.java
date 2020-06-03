package com.example.demo.mapper;

import com.example.demo.repositroy.Permission;
import com.example.demo.repositroy.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhanghaoyang
 */
@Component
public interface UserMapper {
    /**
     * 找所有用户
     *
     * @return
     */
    @Select("SELECT * FROM sys_user")
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "passWord", column = "pass_word"),
            @Result(property = "account", column = "account"),
            @Result(property = "lastLoginTime", column = "last_login_time"),
            @Result(property = "enable", column = "enable"),
            @Result(property = "createTime", column = "create_time")
    })
    List<User> findAll();


    /**
     * @param user
     */
    @Insert("insert into sys_user(account,user_name,pass_word,create_time,sex,birthday,last_login_time) " +
            "values(#{account},#{userName},#{passWord},#{createTime},#{sex},#{birthday},#{lastLoginTime})")
    void insertUser(User user);

    @Update("update sys_user set user_name=#{userName},pass_word=#{pass_word},enable=#{enable}")
    void updateUser(User user);

    @Select("select count(1) from sys_user")
    int countUsers();

    @Select("select count(1) from sys_user where account=#{phone}")
    Boolean existUserByPhone(String phone);

    @Select("select * from sys_user where account=#{phone}")
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "passWord", column = "pass_word"),
            @Result(property = "account", column = "account"),
            @Result(property = "lastLoginTime", column = "last_login_time"),
            @Result(property = "enable", column = "enable"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "birthday", column = "birthday")
    })
    User findUserByPhone(String phone);

    @Select("select id from sys_user where account=#{phone}")
    Long findUserIdByPhone(String phone);

    @Select("select * from sys_user where id=#{id}")
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "passWord", column = "pass_word"),
            @Result(property = "account", column = "account"),
            @Result(property = "lastLoginTime", column = "last_login_time"),
            @Result(property = "enable", column = "enable"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "birthday", column = "birthday")
    })
    User findUserById(Long id);

    @Select("select * from sys_user where account=#{account} and pass_word=#{passWord}")
    @Results({
            @Result(property = "userName", column = "user_name"),
            @Result(property = "passWord", column = "pass_word"),
            @Result(property = "account", column = "account"),
            @Result(property = "lastLoginTime", column = "last_login_time"),
            @Result(property = "enable", column = "enable"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "birthday", column = "birthday")
    })
    User findUserByAccountAndPassword(User user);

    @Select("SELECT\n" +
            "        p.*\n" +
            "        FROM\n" +
            "        sys_user AS u\n" +
            "        LEFT JOIN sys_user_role_relation AS ur\n" +
            "        ON u.id = ur.user_id\n" +
            "        LEFT JOIN sys_role AS r\n" +
            "        ON r.id = ur.role_id\n" +
            "        LEFT JOIN sys_role_permission_relation AS rp\n" +
            "        ON r.id = rp.role_id\n" +
            "        LEFT JOIN sys_permission AS p\n" +
            "        ON p.id = rp.permission_id\n" +
            "        WHERE u.id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "permissionCode", column = "permission_code"),
            @Result(property = "permissionName", column = "permission_name")
    })
    List<Permission> findPermissionListByUserId(Long id);

}
