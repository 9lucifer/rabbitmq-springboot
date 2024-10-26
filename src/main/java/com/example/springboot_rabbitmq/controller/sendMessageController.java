package com.example.springboot_rabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author：丁浩然
 * @Package：com.example.springboot_rabbitmq.controller
 * @Project：springboot_rabbitmq
 * @name：sendMessageController
 * @Date：2024/10/24 1:18
 * @Filename：sendMessageController
 * @Purpose：发送延迟消息
 */
@RestController
@Slf4j
@RequestMapping("/ttl")
public class sendMessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //开始发消息
    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable String message)
    {
        log.info("当前时间：{}，发送一条信息给俩ttl队列:{}",new Date().toString(),message);
        rabbitTemplate.convertAndSend("x","xa","ttl10s"+message);
        rabbitTemplate.convertAndSend("x","xb","ttl40s"+message);
    }

    @GetMapping("/sendEM/{message}/{ttlTime}")
    public void sendEM(@PathVariable String message,@PathVariable String ttlTime){
        log.info("当前时间：{}，发送一条时长为{}毫秒的消息给队列qc:{}",new Date().toString(),ttlTime,message);
        rabbitTemplate.convertAndSend("x","xc",message,message1 -> {
            message1.getMessageProperties().setExpiration(ttlTime);
            return message1;
        });
    }

    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";

    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";


    @GetMapping("sendDelayMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message,@PathVariable Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, message,
                correlationData ->{
                    correlationData.getMessageProperties().setDelay(delayTime);
                    return correlationData;
                });
        log.info(" 当 前 时 间 ： {}, 发 送 一 条 延 迟 {} 毫 秒 的 信 息 给 队 列 delayed.queue:{}", new Date(),delayTime, message);
    }

    //开始发消息。用于测试发布确认


}
