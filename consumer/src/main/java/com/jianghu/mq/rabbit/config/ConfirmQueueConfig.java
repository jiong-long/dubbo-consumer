package com.jianghu.mq.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 发布确认
 * @author: OF3848
 * @create: 2021-08-09 22:41
 */
@Configuration
public class ConfirmQueueConfig {

    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
    public static final String WARNING_EXCHANGE_NAME = "warning.exchange";

    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    public static final String WARNING_QUEUE_NAME = "warning.queue";

    public static final String CONFIRM_ROUTING_KEY = "key1";

    @Bean
    public FanoutExchange warningExchange(){
        return new FanoutExchange(WARNING_EXCHANGE_NAME);
    }

    @Bean
    public DirectExchange confirmExchange(){
        // 定义正常交换机的备份交换机
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).withArgument("alternate-exchange", WARNING_EXCHANGE_NAME).build();
    }

    @Bean
    public Queue confirmQueue(){
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    @Bean
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    @Bean
    public Binding confirmQueueBindingExchange(@Qualifier("confirmExchange") DirectExchange confirmExchange,
                                               @Qualifier("confirmQueue") Queue confirmQueue){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean
    public Binding warningQueueBindingExchange(@Qualifier("warningExchange") FanoutExchange warningExchange,
                                               @Qualifier("warningQueue") Queue warningQueue){
        return BindingBuilder.bind(warningQueue).to(warningExchange);
    }

}
