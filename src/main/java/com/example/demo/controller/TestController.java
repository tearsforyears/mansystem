package com.example.demo.controller;

import com.example.demo.utils.JsonReponseBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author zhanghaoyang
 */
@RestController
public class TestController {
    @GetMapping("/ping")
    String ping1() {
        return "{msg:'pong get req'}";
    }

    @PostMapping("/ping")
    String ping2() {
        return "{msg:'pong post req'}";
    }

    @PutMapping("/ping")
    String ping3() {
        return "{msg:'pong put req'}";
    }

    @DeleteMapping("/ping")
    String ping4() {
        return "{msg:'pong delete req'}";
    }


}
