package com.example.demo.handler;

import com.example.demo.service.InOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zhanghaoyang
 */
@Component
public class AlertSchedulerTaskHandler {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    InOutService inOutService;

    @Scheduled(cron = "*/10 * * * * ?")
    private void process() {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();

        valueOperations.set("AlertInfo", inOutService.findAlertInfo());
        // 警告的详细信息
        valueOperations.set("AlertAmount", inOutService.countAlertInfo().toString());
        // 警告人数
    }
}
