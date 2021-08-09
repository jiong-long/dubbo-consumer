package com.jianghu.mq.rabbit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description: 消息确认
 * 还要再配置文件中进行配置
 * @author: OF3848
 * @create: 2021-08-10 00:00
 */
@Slf4j
@Component
public class MyConfirm implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if(ack){
            log.info("消息{}发送成功" , id);
        } else {
            log.info("消息{}发送失败：{}" , id, cause);
        }
    }
}
