package com.example.springboot_rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author：丁浩然
 * @Package：com.example.springboot_rabbitmq.config
 * @Project：springboot_rabbitmq
 * @name：MayCallBack
 * @Date：2024/10/24 12:15
 * @Filename：MayCallBack
 * @Purpose：null
 */
@Component
@Slf4j
public  class MayCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{

    //注入接口
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 回调方法参数和情况解析
     * 1.接收到了消息-->回调
     *  1.1 correlationData保存回调消息的id以及相关消息
     *  1.2 交换机收到消息--> ack = true
     *  1.3 cause null
     *2.发消息 接受失败的回调函数
     *  2.1  correlationData保存回调消息的id以及相关消息
     *  2.2  交换机收到消息--> ack = false
     *  2.3  cause 失败的原因

     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        if(ack){
            log.info("交换机已经收到id为{}的消息",correlationData.getId()!=null?correlationData.getId():"");
        }else {
            log.info("交换金还没收到id为{}的消息，原因：{}",correlationData.getId()!=null?correlationData.getId():"",cause);
        }


    }

    //在消息 不可以到达目 的地时将消息返还给生产者

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String
            exchange, String routingKey) {
        log.error(" 消 息 {}, 被 交 换 机 {} 退 回 ， 退 回 原 因 :{}, 路 由 key:{}",new

                String(message.getBody()),exchange,replyText,routingKey);
    }


}
