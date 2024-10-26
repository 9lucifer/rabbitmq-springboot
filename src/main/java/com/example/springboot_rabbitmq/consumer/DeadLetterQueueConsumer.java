package com.example.springboot_rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author：丁浩然
 * @Package：com.example.springboot_rabbitmq.consumer
 * @Project：springboot_rabbitmq
 * @name：DeadLetterQueueConsumer
 * @Date：2024/10/24 1:24
 * @Filename：DeadLetterQueueConsumer
 * @Purpose：null
 */
@Component
@Slf4j
public class DeadLetterQueueConsumer {
    @RabbitListener(queues = "qd")
    public void receiveD(Message message, Channel channel)throws Exception{
        String msg = new String(message.getBody().toString());
        log.info("当前时间：{}，发送一条信息给俩ttl队列:{}",new Date().toString(),msg);

    }

}
