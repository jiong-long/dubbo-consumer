package com.jianghu.mq.rabbit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @description: topic 消费者
 * @author: OF3848
 * @create: 2021-08-06 23:12
 */
public class Topic {

    private final static String EXCHANGE_NAME = "topic_name";

    public static void main(String[] args) {
        // 接收消息，不可以直接关闭Channel
        try{
            Channel channel = RabbitMqUtils.getChannel();
            // 声明交换机（分发:发布/订阅模式）
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            // 声明队列
            final String queueName = channel.queueDeclare().getQueue();
            // 将队列绑定到交换机
            channel.queueBind(queueName, EXCHANGE_NAME, "*.home");
            // 声明队列
            final String queueName1 = channel.queueDeclare().getQueue();
            // 将队列绑定到交换机
            channel.queueBind(queueName1, EXCHANGE_NAME, "home.*");

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                System.out.println("*.home 收到消息");
                System.out.println(message.getEnvelope().getRoutingKey() + ":" + new String(message.getBody()));
                // 确认收到信息
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            };

            DeliverCallback deliverCallback1 = (consumerTag, message) -> {
                System.out.println("home.* 收到消息");
                System.out.println(message.getEnvelope().getRoutingKey() + ":" + new String(message.getBody()));
                // 确认收到信息
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            };

            CancelCallback cancelCallback = consumerTag -> {};

            channel.basicConsume(queueName, false, deliverCallback, cancelCallback);
            channel.basicConsume(queueName1, false, deliverCallback1, cancelCallback);

            System.out.println(queueName + "等待接收消息");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
