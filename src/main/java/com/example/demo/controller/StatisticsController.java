package com.example.demo.controller;

import com.example.demo.utils.JsonReponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanghaoyang
 */
@RestController
public class StatisticsController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/alert/getAmount")
    public String getAmount() {
        return new JsonReponseBuilder().addItems(
                "state", "200",
                "amount", stringRedisTemplate.opsForValue().get("AlertAmount")
        ).build();
    }

    @GetMapping("/alert/getAlertInfo")
    public String getAlertInfo() {
        return stringRedisTemplate.opsForValue().get("AlertInfo");
    }

    @GetMapping("/statistics/getInTPSInfo")
    public String getInTPSInfo() {
        return stringRedisTemplate.opsForValue().get("InTPS");
    }

    @GetMapping("/statistics/getOutTPSInfo")
    public String getOutTPSInfo() {
        return stringRedisTemplate.opsForValue().get("OutTPS");
    }
}
