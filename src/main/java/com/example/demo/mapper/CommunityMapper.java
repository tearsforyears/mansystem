package com.example.demo.mapper;

import com.example.demo.repositroy.Community;
import com.example.demo.repositroy.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhanghaoyang
 */
@Component
public interface CommunityMapper {
    @Select("SELECT * FROM sys_community where id=#{id}")
    Community findCommunityById(String id);

    @Select("SELECT * FROM sys_community")
    List<Community> findAll();

    @Select("SELECT * FROM sys_community where id in (SELECT community_id FROM sys_user_community_authorized where user_id=#{id})")
    List<Community> findAllManageComByUserid(Long id);

    @Select("SELECT * FROM sys_user u left join sys_user_community c on u.id=c.user_id where community_id=#{id}")
    List<User> findAllUsersByCommunityId(Long id);

    @Select("SELECT * FROM sys_community where id in (SELECT community_id FROM sys_user_community where user_id=#{id})")
    Community findCommunityByUserId(Long id);

    @Select("SELECT * FROM sys_community limit #{from},#{size}")
    List<Community> findAllByPages(Integer from, Integer size);

    @Select("SELECT count(1) from sys_community")
    Integer count();

    @Insert("INSERT INTO sys_user_community (user_id,community_id) values(#{uid},#{comId})")
    void insertUserToCommunity(Integer uid, Integer comId);

    @Insert("INSERT INTO sys_community(name,description,address) values(#{name},#{description},#{address}) ")
    void insertCommunity(Community community);


}
