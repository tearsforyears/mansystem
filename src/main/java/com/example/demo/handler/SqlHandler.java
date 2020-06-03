package com.example.demo.handler;

import com.alibaba.fastjson.JSON;
import com.example.demo.repositroy.In;
import com.example.demo.repositroy.Out;
import com.example.demo.service.InOutService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhanghaoyang
 */
@Component
public class SqlHandler {
    @Autowired
    InOutService inOutService;

    @RabbitListener(queues = "com.example.demo.inout.insert")
    public void insertInOut1(String json) {
        Out out = JSON.parseObject(json, Out.class);
        inOutService.reduceOut(out);
    }

    @RabbitListener(queues = "com.example.demo.inout.update")
    public void updateInOut2(String json) {
        In in = JSON.parseObject(json, In.class);
        inOutService.reduceIn(in);
    }
}
