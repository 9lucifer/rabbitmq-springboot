package com.example.springboot_rabbitmq.consumer;

import com.example.springboot_rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author：丁浩然
 * @Package：com.example.springboot_rabbitmq.consumer
 * @Project：springboot_rabbitmq
 * @name：Consumer
 * @Date：2024/10/24 12:02
 * @Filename：Consumer
 * @Purpose：null
 */
@Component
@Slf4j
public class Consumer {

    @RabbitListener(queues = ConfirmConfig.confirm_queue_name)
    public  void receiveConfirm(Message message) {
        System.out.println(message.getBody());
        log.info("接收到的队列消息是：{}",new String(message.getBody()));
    }
}
