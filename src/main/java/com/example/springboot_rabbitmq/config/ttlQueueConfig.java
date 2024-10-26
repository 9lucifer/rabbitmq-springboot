package com.example.springboot_rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author：丁浩然
 * @Package：com.example.springboot_rabbitmq.config
 * @Project：springboot_rabbitmq
 * @name：ttlQueueConfig
 * @Date：2024/10/24 0:52
 * @Filename：ttlQueueConfig
 * @Purpose：ttl配置文件类
 */
@Configuration
public class ttlQueueConfig {
    //普通交换机的名称
    public static final String x_exchange = "x";
    //死信交换机的名称
    public static final String y_dead_letter_exchange = "y";
    //普通队列的名称
    public static final String queue_A = "qa";
    public static final String queue_B = "qb";
    //死信队列的名称
    public static final String dead_letter_queue_D = "qd";

    public static final String queue_C = "qc";
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(x_exchange);
    }

    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(y_dead_letter_exchange);
    }

    @Bean("queueA")
    public Queue queueA(){
        Map<String, Object>argument = new HashMap<>();
        argument.put("x-dead-letter-exchange",y_dead_letter_exchange);
        argument.put("x-dead-letter-routing-key","yd");
        argument.put("x-message-ttl",10000);
        return QueueBuilder.durable(queue_A).withArguments(argument).build();
    }

    @Bean("queueB")
    public Queue queueB(){
        Map<String, Object>argument = new HashMap<>();
        argument.put("x-dead-letter-exchange",y_dead_letter_exchange);
        argument.put("x-dead-letter-routing-key","yd");
        argument.put("x-message-ttl",40000);
        return QueueBuilder.durable(queue_B).withArguments(argument).build();
    }

    @Bean("queueC")
    public Queue queueC(){
        Map<String, Object>argument = new HashMap<>();
        argument.put("x-dead-letter-exchange",y_dead_letter_exchange);
        argument.put("x-dead-letter-routing-key","yd");
        return QueueBuilder.durable(queue_C).withArguments(argument).build();
    }

    @Bean("queueD")
    public Queue queueD(){
        return QueueBuilder.durable(dead_letter_queue_D).build();
    }
    @Bean
    public Binding queueABindingX(@Qualifier("queueA")Queue queueA,
                                  @Qualifier("xExchange")DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("xa");
    }
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB")Queue queueB,
                                  @Qualifier("xExchange")DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("xb");
    }

    @Bean
    public Binding queueDBindingX(@Qualifier("queueD")Queue queueD,
                                  @Qualifier("yExchange")DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("yd");
    }

    @Bean
    public Binding queueCBindingX(@Qualifier("queueC")Queue queueC,
                                  @Qualifier("xExchange")DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("xc");
    }
}
