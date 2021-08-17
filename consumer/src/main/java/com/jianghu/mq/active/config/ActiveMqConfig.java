package com.jianghu.mq.active.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @description:
 * @author: OF3848
 * @create: 2021-08-17 22:59
 */
@Configuration
public class ActiveMqConfig {

    @Bean
    public Queue queue(){
        return new ActiveMQQueue("queue-name");
    }

    @Bean
    public Topic topic(){
        return new ActiveMQTopic("topic-name");
    }

}
