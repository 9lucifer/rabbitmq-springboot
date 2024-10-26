package com.example.springboot_rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author：丁浩然
 * @Package：com.example.springboot_rabbitmq.config
 * @Project：springboot_rabbitmq
 * @name：ConfirmConfig
 * @Date：2024/10/24 11:48
 * @Filename：ConfirmConfig
 * @Purpose：发布确认高级
 */
@Configuration
public class ConfirmConfig {
    // 交换机
    public static final String confirm_exchange_name = "confirm_exchange_name";
    // 队列
    public static final String confirm_queue_name = "confirm_queue_name";
    // routing-key
    public static final String confirm_routing_key = "key1";
    // 备份交换机
    public static final String backup_queue_name = "backup_queue_name";
    public static final String backup_exchange_name = "backup_exchange_name";
    public static final String warning_queue_name = "warning_queue_name";

    // 声明交换机
    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {
        return ExchangeBuilder.directExchange(confirm_exchange_name)
                .durable(true)
                .withArgument("alternate-exchange", backup_exchange_name)
                .build();
    }

    @Bean("confirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.durable(confirm_queue_name).build();
    }

    @Bean("backupExchange")
    public FanoutExchange backupExchange() {
        return new FanoutExchange(backup_exchange_name);
    }

    @Bean("backupQueue")
    public Queue backupQueue() {
        return QueueBuilder.durable(backup_queue_name).build();
    }

    @Bean("warningQueue")
    public Queue warningQueue() {
        return QueueBuilder.durable(warning_queue_name).build();
    }

    @Bean
    public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue confirmQueue,
                                        @Qualifier("confirmExchange") DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(confirm_routing_key);
    }

    @Bean
    public Binding BqueueBindingBExchange(@Qualifier("backupQueue") Queue backupQueue,
                                          @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    @Bean
    public Binding WqueueBindingBExchange(@Qualifier("warningQueue") Queue warningQueue,
                                          @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
}
