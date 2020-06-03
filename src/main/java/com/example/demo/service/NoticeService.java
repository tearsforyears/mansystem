package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.repositroy.Message;
import com.example.demo.utils.JsonReponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanghaoyang
 */
@Service
public class NoticeService {

    @Autowired
    MessageMapper messageMapper;

    /**
     * 获取社区内的所有消息
     * @param id 社区id
     * @return
     */
    public String getMessageByCommunityId(String id) {
        try {
            return JSON.toJSONString(messageMapper.findMessagesByCommunityId(new Long(id)));
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonReponseBuilder().addItems("state", "200", "error", "1").build();
        }
    }

    /**
     * 往社区添加消息
     * @param id 社区id
     * @param msg 消息本体
     * @return
     */
    public String addMessage(String id, Message msg) {
        try {
            messageMapper.insertMessage(new Long(id), msg);
            return new JsonReponseBuilder().addItems("state", "200", "msg", "消息发布成功").build();
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonReponseBuilder().addItems("state", "200", "msg", "消息发布失败").build();
        }
    }

    /**
     * 删除社区消息
     * @param id 消息id
     * @return
     */
    public String deleteMessage(String id) {
        try {
            messageMapper.deleteMessage(new Long(id));
            return new JsonReponseBuilder().addItems("state", "200", "msg", "消息删除成功").build();
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonReponseBuilder().addItems("state", "200", "msg", "消息删除失败").build();
        }
    }
}
