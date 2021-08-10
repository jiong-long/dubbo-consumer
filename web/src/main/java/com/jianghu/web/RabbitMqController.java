package com.jianghu.web;

import com.jianghu.mq.rabbit.config.ConfirmQueueConfig;
import com.jianghu.mq.rabbit.config.DelayedQueueConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: rabbitMQ
 * @author: OF3848
 * @create: 2021-08-08 22:34
 */
@RestController
@RequestMapping("/rabbitmq")
public class RabbitMqController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg")
    @ApiOperation("与SpringBoot集成")
    public void sendMessage(){
        String message = "你好啊!!!";
        rabbitTemplate.convertAndSend("X", "XA", "来自ttl为10S的队列：" + message);
        rabbitTemplate.convertAndSend("X", "XB", "来自ttl为40S的队列：" + message);
    }

    /**
     * 过期时间不与队列绑定
     * 验证 原生的RabbitMQ不支持延迟消息
     */
    @GetMapping("/sendMsg2")
    @ApiOperation("原生不支持延迟队列")
    public void sendMessage2(){
        String message = "你好啊!!!";
        // 动态设置消息过期时间
        MessagePostProcessor messagePostProcessor = message1 -> {
            message1.getMessageProperties().setExpiration("11000");
            return message1;
        };
        rabbitTemplate.convertAndSend("X", "XC", "先发：延迟11000：" + message, messagePostProcessor);

        // 该消息后发，延迟时间短，但是要等前面的消息都延迟后改消息才延迟
        MessagePostProcessor messagePostProcessor2 = message1 -> {
            message1.getMessageProperties().setExpiration("1000");
            return message1;
        };
        rabbitTemplate.convertAndSend("X", "XC", "后发：延迟1000：" + message, messagePostProcessor2);
    }

    /**
     * 基于插件实现延迟队列
     */
    @GetMapping("/sendMsg3")
    @ApiOperation("延迟队列")
    public void sendMessage3(){
        String message = "你好啊!!!";
        // 动态设置消息过期时间
        MessagePostProcessor messagePostProcessor = message1 -> {
            // 这里不是setExpiration了，要注意
            message1.getMessageProperties().setDelay(11000);
            return message1;
        };
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY,
                "先发：延迟11000：" + message, messagePostProcessor);

        MessagePostProcessor messagePostProcessor2 = message1 -> {
            message1.getMessageProperties().setDelay(100);
            return message1;
        };
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY,
                "后发：延迟1000：" + message, messagePostProcessor2);
    }

    @GetMapping("/sendMsg4")
    @ApiOperation("发布确认")
    public void sendMessage4(){
        String message = "你好啊!!!";
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmQueueConfig.CONFIRM_EXCHANGE_NAME + "1", ConfirmQueueConfig.CONFIRM_ROUTING_KEY,
                message + "【发送交换机失败的消息】", correlationData);

        CorrelationData correlationData2 = new CorrelationData("2");
        rabbitTemplate.convertAndSend(ConfirmQueueConfig.CONFIRM_EXCHANGE_NAME, ConfirmQueueConfig.CONFIRM_ROUTING_KEY + "1",
                message + "【发送routingKey失败的消息】", correlationData2);

        CorrelationData correlationData3 = new CorrelationData("3");
        rabbitTemplate.convertAndSend(ConfirmQueueConfig.CONFIRM_EXCHANGE_NAME, ConfirmQueueConfig.CONFIRM_ROUTING_KEY,
                message + "【成功的消息】", correlationData3);
    }
}
