package com.jianghu.mq.rabbit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @description: 发布订阅模式---订阅者
 * @author: OF3848
 * @create: 2021-08-06 15:01
 */
public class Subscribe {

    private final static String EXCHANGE_NAME = "exchange_name";

    public static void main(String[] args) {
        // 接收消息，不可以直接关闭Channel
        try{
            Channel channel = RabbitMqUtils.getChannel();
            // 声明交换机（分发:发布/订阅模式）
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            // 声明队列
            final String queueName = channel.queueDeclare().getQueue();
            // 将队列绑定到交换机
            channel.queueBind(queueName, EXCHANGE_NAME, "");

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                System.out.println(new String(message.getBody()));
                // 确认收到信息
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            };

            CancelCallback cancelCallback = consumerTag -> {};

            channel.basicConsume(queueName, false, deliverCallback, cancelCallback);

            System.out.println(queueName + "等待接收消息");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
