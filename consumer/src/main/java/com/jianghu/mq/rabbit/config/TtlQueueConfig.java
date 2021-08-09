package com.jianghu.mq.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: TTL SpringBoot 配置类
 * @author: OF3848
 * @create: 2021-08-08 22:10
 */
@Configuration
public class TtlQueueConfig {
    // 普通交换机
    public static final String X_EXCHANGE = "X";
    // 死信交换机
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    // 普通队列
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    // 死信队列
    public static final String DEAD_LETTER_QUEUE = "QD";

    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    @Bean("queueA")
    public Queue queueA(){
        Map<String, Object> arguments = new HashMap<>(3);
        // 死信交换机
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        // 死信routing-key
        arguments.put("x-dead-letter-routing-key", "YD");
        // 设置TTL
        arguments.put("x-message-ttl", 10 * 1000);
        return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();
    }

    @Bean("queueB")
    public Queue queueB(){
        Map<String, Object> arguments = new HashMap<>(3);
        // 死信交换机
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        // 死信routing-key
        arguments.put("x-dead-letter-routing-key", "YD");
        // 设置TTL
        arguments.put("x-message-ttl", 40 * 1000);
        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
    }

    /**
     * 不设置ttl，有产生消息的地方动态指定
     * @return
     */
    @Bean("queueC")
    public Queue queueC(){
        Map<String, Object> arguments = new HashMap<>(3);
        // 死信交换机
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        // 死信routing-key
        arguments.put("x-dead-letter-routing-key", "YD");
        return QueueBuilder.durable(QUEUE_C).withArguments(arguments).build();
    }

    @Bean("queueD")
    public Queue queueD(){
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    /**
     * 绑定
     * @param queueA
     * @param xExchange
     * @return
     */
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }

    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD,
                                  @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }

}
