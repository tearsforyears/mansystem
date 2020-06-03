package com.example.demo.controller;

import com.example.demo.repositroy.In;
import com.example.demo.repositroy.Message;
import com.example.demo.repositroy.Out;
import com.example.demo.service.InOutService;
import com.example.demo.service.NoticeService;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zhanghaoyang
 */
@RestController
public class ManageController {
    @Autowired
    NoticeService noticeService;
    @Autowired
    InOutService inOutService;

    @PostMapping("/manage/postMessage")
    String postMessage(String token, String id, String title, String content, String importance) {
        Message msg = new Message();
        msg.setImportance(Integer.parseInt(importance));
        msg.setTitle(title);
        msg.setContent(content);
        return noticeService.addMessage(id, msg);
    }

    @GetMapping("/manage/getMessages")
    String getMessages(String token, String id) {
        return noticeService.getMessageByCommunityId(id);
    }

    @DeleteMapping("/manage/deleteMessage")
    String deleteMessage(String token, String id) {
        return noticeService.deleteMessage(id);
    }

    /**
     * 该方法使用了rabbitmq做缓冲
     * @param token
     * @param phone
     * @param comment
     * @param fromCommunityId
     * @return
     */
    @PostMapping("/manage/outCommunity")
    String outCommunity(String token, String phone, String comment, Long fromCommunityId) {
        Out out = new Out();
        out.setOutUserId(new Long(JwtUtil.getTokenValue(token, "uid")));
        out.setComment(comment);
        out.setFromCommunityId(fromCommunityId);
        return inOutService.out(out, phone);
    }

    /**
     * 该方法使用了rabbitmq做缓冲
     * @param token
     * @param phone
     * @param comment
     * @param toCommunityId
     * @return
     */
    @PostMapping("/manage/inCommunity")
    String inCommunity(String token, String phone, String comment, Long toCommunityId) {
        In in = new In();
        in.setInUserId(new Long(JwtUtil.getTokenValue(token, "uid")));
        in.setComment(comment);
        in.setToCommunityId(toCommunityId);
        return inOutService.in(in, phone);
    }
}
