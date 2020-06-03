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
public class StatisticsSchedulerTaskHandler {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    InOutService inOutService;

    @Scheduled(cron = "0 0 0/1 * * ?") // debug模式的每分调度一次  0 * */1 * * ?
    private void process() {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set("InTPS", inOutService.getInTPSInfo());
        valueOperations.set("OutTPS", inOutService.getOutTPSInfo());
    }
}
