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
 * @name：warningConsumer
 * @Date：2024/10/25 17:48
 * @Filename：warningConsumer
 * @Purpose：null
 */
@Component
@Slf4j
public class warningConsumer {

    //接受报警消息
    @RabbitListener(queues = ConfirmConfig.warning_queue_name)
    public void receiveWarning(Message message){
        String msg = new String(message.getBody());
        log.error("报警发现无法路由，消息是：{}",msg);
    }
}
