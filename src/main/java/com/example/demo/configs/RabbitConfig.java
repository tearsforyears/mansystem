package com.example.demo.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhanghaoyang
 * rabbitmq统一配置 因为该系统业务相对简单所以我们
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue insertQueue() {
        return new Queue("com.example.demo.inout.insert", true);
    }
    @Bean
    public Queue updateQueue() {
        return new Queue("com.example.demo.inout.update", true);
    }

    @Bean
    public DirectExchange sqlDirectExchange() {
        return new DirectExchange("sqlDirectExchange", true, true);
    }

    @Bean
    public Binding bindingDirect1() {
        Binding bindingBuilder =
                BindingBuilder.bind(insertQueue()).to(sqlDirectExchange()).with("insert");
        return bindingBuilder;
    }

    @Bean
    public Binding bindingDirect2() {
        Binding bindingBuilder =
                BindingBuilder.bind(updateQueue()).to(sqlDirectExchange()).with("update");
        return bindingBuilder;
    }
}
