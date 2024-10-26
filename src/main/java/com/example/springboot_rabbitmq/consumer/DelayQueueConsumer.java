package com.example.springboot_rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author：丁浩然
 * @Package：com.example.springboot_rabbitmq.consumer
 * @Project：springboot_rabbitmq
 * @name：DelayQueueConsumer
 * @Date：2024/10/24 3:21
 * @Filename：DelayQueueConsumer
 * @Purpose：null
 */
@Slf4j
@Component
public class DelayQueueConsumer {
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    @RabbitListener(queues = DELAYED_QUEUE_NAME)

    public void receiveDelayedQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到延时队列的消息：{}", new Date().toString(), msg);
    }

}
