package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.mapper.CommunityMapper;
import com.example.demo.repositroy.Community;
import com.example.demo.utils.JsonReponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhanghaoyang
 */
@Service
public class CommunityService {

    @Autowired
    CommunityMapper communityMapper;

    public String findManageCommunity(String id) {
        return JSON.toJSONString(communityMapper.findCommunityById(id));
    }

    public String findManageCommunities(String uid) {
        return JSON.toJSONString(communityMapper.findAllManageComByUserid(new Long(uid)));
    }

    public String findCommunityByUserId(String id) {
        return JSON.toJSONString(communityMapper.findCommunityByUserId(new Long(id)));
    }

    public String findAll() {
        return JSON.toJSONString(communityMapper.findAll());
    }

    public Integer count() {
        return communityMapper.count();
    }

    public String findAll(Integer page, Integer pageSize) {
        Integer from = page * pageSize;
        return JSON.toJSONString(communityMapper.findAllByPages(from, pageSize));
    }

    public String joinCommunity(String uid, String comId) {
        try {
            communityMapper.insertUserToCommunity(Integer.parseInt(uid), Integer.parseInt(comId));
            return "加入成功";
        } catch (Exception e) {
            return "加入失败";
        }
    }

    public String insertCommunity(Community com) {
        try {
            communityMapper.insertCommunity(com);
            return new JsonReponseBuilder().addItems(
                    "state", "200",
                    "msg", "添加成功"
            ).build();
        } catch (Exception e) {
            return new JsonReponseBuilder().addItems(
                    "state", "200",
                    "msg", "添加失败"
            ).build();
        }
    }

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 让用户管理社区
     * 本质上是加入redis等待审核真正写入数据库
     * 所以这里是需要写入redis
     *
     * @param uid 前台用户的id
     * @param id  社区id
     * @return
     */
    public String insertManCommunityUsers(String uid, String id) {
        try {
            String json = new JsonReponseBuilder().addItems("uid", uid, "id", id).build().toString();
            redisTemplate.opsForSet().add("verify_set", json);
            return new JsonReponseBuilder().addItems(
                    "state", "200",
                    "msg", "添加成功"
            ).build();
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonReponseBuilder().addItems(
                    "state", "200",
                    "msg", "添加失败"
            ).build();
        }
    }
}
