package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.mapper.InOutMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repositroy.In;
import com.example.demo.repositroy.Out;
import com.example.demo.repositroy.User;
import com.example.demo.utils.JsonReponseBuilder;
import com.example.demo.utils.SpringContextUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.TimeZone;

/**
 * 该系统的核心逻辑,rabbitmq对读写操作进行缓冲
 * 提供统计等复杂业务逻辑
 * @author zhanghaoyang
 */
@Service
public class InOutService {
    @Autowired
    InOutMapper inOutMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 查看用户手否出门了 如果已经外出了则更新入状态到数据否
     * 否则则不予理会
     * @param in
     */
    public void reduceIn(In in) {
        if (inOutMapper.findIfUserOut(in.getUserId())) {
            inOutMapper.in(in);
        } else {
            // 如果人不在外出状态 就是非法请求不予理会
        }
    }

    /**
     * 查看用户是否出门了 如果已经外出了不做任何操作
     * 如果没有外出则把外出信息更新到数据库
     * @param out
     */
    public void reduceOut(Out out) {
        if (inOutMapper.findIfUserOut(out.getUserId())) {
            // 已经外出了 非法状态
        } else {
            // 插入外出信息
            inOutMapper.out(out);
        }
    }

    /**
     * 登记入信息
     * @param in 入信息封装
     * @param phone 手机号
     * @return
     */
    public String in(In in, String phone) {
        try {
            UserService userService = SpringContextUtil.getBean(UserService.class);
            Long id = userService.getUserIdByPhone(phone);
            if (id != null) {
                if (inOutMapper.findIfUserOut(id)) {
                    // 如果用户在外出中则可以加入社区
                    TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
                    in.setUserId(id);
                    in.setInTime(new Date());
                    rabbitTemplate.convertAndSend("sqlDirectExchange", "update", JSON.toJSONString(in));
                    return new JsonReponseBuilder().addItems(
                            "state", "200",
                            "msg", "操作成功"
                    ).build();
                } else {
                    return new JsonReponseBuilder().addItems(
                            "state", "200",
                            "msg", "用户不在外出中请重复确认登记用户身份信息"
                    ).build();
                }
            } else {
                return new JsonReponseBuilder().addItems(
                        "state", "200",
                        "msg", "用户手机号不存在"
                ).build();
            }
        } catch (Exception e) {
            return new JsonReponseBuilder().addItems(
                    "state", "200",
                    "msg", "操作失败"
            ).build();
        }
    }

    /**
     * 登记用户出
     * @param out 出信息封装
     * @param phone 手机号
     * @return
     */
    public String out(Out out, String phone) {
        try {
            UserService userService = SpringContextUtil.getBean(UserService.class);
            Long id = userService.getUserIdByPhone(phone);
            if (id != null) {
                if (!inOutMapper.findIfUserOut(id)) {
                    // 如果用户在不在外出中或第一次登记则可以外出操作
                    TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
                    out.setUserId(id);
                    out.setOutTime(new Date());
                    rabbitTemplate.convertAndSend("sqlDirectExchange", "insert", JSON.toJSONString(out));
                    return new JsonReponseBuilder().addItems(
                            "state", "200",
                            "msg", "操作成功"
                    ).build();
                } else {
                    // 如果用户在外出中则不可重复外出
                    return new JsonReponseBuilder().addItems(
                            "state", "200",
                            "msg", "用户在外出中不可重复修改信息"
                    ).build();
                }
            } else {
                return new JsonReponseBuilder().addItems(
                        "state", "200",
                        "msg", "用户手机号不存在"
                ).build();
            }
        } catch (Exception e) {
            return new JsonReponseBuilder().addItems(
                    "state", "200",
                    "msg", "操作失败"
            ).build();
        }
    }

    public String getInOutInfo(Long id) {
        return JSON.toJSONString(inOutMapper.findInOutInfoByUserId(id));
    }

    /**
     * 用户的出入量信息(分页)
     * @param id 用户id
     * @param page 页数
     * @param pageSize 页的大小
     * @return
     */
    public String getInOutInfo(String id, Integer page, Integer pageSize) {
        Integer from = page * pageSize;
        return JSON.toJSONString(inOutMapper.findInOutInfoByUserIdPages(id, from, pageSize));
    }

    /**
     * 出入量总共条数
     * @param id
     * @return
     */
    public String countInOutInfo(String id) {
        return new JsonReponseBuilder().addItems(
                "state", "200",
                "amount", inOutMapper.countInOutInfoByUserId(id).toString()
        ).build();
    }
    /**
     * 某个用户的警告数量
     * @param id 用户id
     * @return
     */
    public Integer countAlertInfoByUserId(String id) {
        return inOutMapper.countAlertInfoByUserId(id);
    }

    /**
     * 某个用户的警告
     * @param id 用户id
     * @return
     */
    public String findAlertInfoByUserId(String id) {
        return JSON.toJSONString(inOutMapper.findAlertInfoByUserId(id));
    }

    // 大于limitTime的被加入警告列表
    private static final String limitTime = "10";

    /**
     * 出入频繁警告信息数量
     * @return
     */
    public Integer countAlertInfo() {
        return inOutMapper.countAlertInfo(limitTime);
    }

    /**
     * 出入频繁警告信息
     * @return
     */
    public String findAlertInfo() {
        return JSON.toJSONString(inOutMapper.findAlertInfo(limitTime));
    }

    /**
     * 入(吞吐量)统计
     * @return
     */
    public String getInTPSInfo() {
        return JSON.toJSONString(inOutMapper.findInTPSInfo());
    }

    /**
     * 出(吞吐量)统计
     * @return
     */
    public String getOutTPSInfo() {
        return JSON.toJSONString(inOutMapper.findOutTPSInfo());
    }

}
