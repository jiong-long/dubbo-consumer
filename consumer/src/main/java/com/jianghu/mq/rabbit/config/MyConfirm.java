package com.jianghu.mq.rabbit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @description: 消息确认
 * 还要再配置文件中进行配置
 * @author: OF3848
 * @create: 2021-08-10 00:00
 */
@Slf4j
//@Component
public class MyConfirm implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 消息发送到交换机后回调事件
     * spring.rabbitmq.publisher-confirm-type=correlated
     * @param correlationData
     * @param ack 是否发送到交换机
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if(ack){
            log.info("消息{}发送成功" , id);
        } else {
            log.info("消息{}发送失败：{}" , id, cause);
        }
    }

    /**
     * 消息没有发送到队列回调事件
     * spring.rabbitmq.publisher-returns=true
     * 如果申明了alternate-exchange，则不会调用该方法
     * @param returnedMessage
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("消息被退回：{}" , new String(returnedMessage.getMessage().getBody()));
    }
}
