package com.example.springboot_rabbitmq.controller;

import com.example.springboot_rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
 * @name：Producer
 * @Date：2024/10/24 11:57
 * @Filename：Producer
 * @Purpose：测试确认
 */
@RestController
@RequestMapping("/confirm")
@Slf4j
public class ProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable String message){
        CorrelationData correlationData = new CorrelationData("1");

       rabbitTemplate.convertAndSend(ConfirmConfig.confirm_exchange_name,
               ConfirmConfig.confirm_routing_key+"123",message,correlationData);
        log.info("当前时间：{}，发送一条信息给队列:{}",new Date().toString(),message);
    }



}
