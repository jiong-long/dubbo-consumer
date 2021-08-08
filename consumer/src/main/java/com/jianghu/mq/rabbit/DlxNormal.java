package com.jianghu.mq.rabbit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @description: 死信--正常消费者
 * @author: OF3848
 * @create: 2021-08-07 23:57
 */
public class DlxNormal {

    /**
     * 正常交换机、队列
     */
    private final static String NORMAL_EXCHANGE_NAME = "normal_exchange_name";
    private final static String NORMAL_QUEUE_NAME = "normal_queue_name";
    /**
     * 死信交换机、队列
     */
    private final static String DEAD_EXCHANGE_NAME = "dead_exchange_name";
    private final static String DEAD_QUEUE_NAME = "dead_queue_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        // 正常交换机队列
        channel.exchangeDeclare(NORMAL_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 配置死信
        Map<String, Object> arguments = new HashMap<>(3);
        // 死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE_NAME);
        // 死信routing-key
        arguments.put("x-dead-letter-routing-key", "dead");
        // 正常队列最大长度
        arguments.put("x-max-length", 6);
        channel.queueDeclare(NORMAL_QUEUE_NAME, false, false, false, arguments);
        channel.queueBind(NORMAL_QUEUE_NAME, NORMAL_EXCHANGE_NAME, "normal");


        channel.exchangeDeclare(DEAD_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DEAD_QUEUE_NAME, false, false, false, null);
        channel.queueBind(DEAD_QUEUE_NAME, DEAD_EXCHANGE_NAME, "dead");


        System.out.println("正常消费者开始接受消息......");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            System.out.println(new String(message.getBody()));
        };

        CancelCallback cancelCallback = consumerTag -> {};

        channel.basicConsume(NORMAL_QUEUE_NAME, false, deliverCallback, cancelCallback);
    }
}
