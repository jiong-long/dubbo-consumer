package com.jianghu.mq.rabbit;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @description: rabbitMQ 消费者
 * @author: OF3848
 * @create: 2021-08-05 19:02
 */
public class Consumer {

    private final static String QUEUE_NAME = "queue_name";

    public static void main(String[] args) {
        // 接收消息，不可以直接关闭Channel
        try{
            Channel channel = RabbitMqUtils.getChannel();
            // 0：轮询分发（默认）；1：不公平分发；其他：预取值
            // 服务器最大消息数
            channel.basicQos(1);

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                System.out.println(new String(message.getBody()));
                // 确认收到信息
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            };

            CancelCallback cancelCallback = consumerTag -> {};

            channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
