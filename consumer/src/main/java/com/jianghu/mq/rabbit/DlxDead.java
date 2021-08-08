package com.jianghu.mq.rabbit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @description: 死信--死信消费者
 * @author: OF3848
 * @create: 2021-08-07 23:57
 */
public class DlxDead {

    /**
     * 死信交换机、队列
     */
    private final static String DEAD_EXCHANGE_NAME = "dead_exchange_name";
    private final static String DEAD_QUEUE_NAME = "dead_queue_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(DEAD_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DEAD_QUEUE_NAME, false, false, false, null);
        channel.queueBind(DEAD_QUEUE_NAME, DEAD_EXCHANGE_NAME, "dead");

        System.out.println("死信消费者开始接受消息......");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println( new String(message.getBody()));
        };

        CancelCallback cancelCallback = consumerTag -> {};

        channel.basicConsume(DEAD_QUEUE_NAME, false, deliverCallback, cancelCallback);
    }
}
