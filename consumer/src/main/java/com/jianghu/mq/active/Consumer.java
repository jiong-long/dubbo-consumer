package com.jianghu.mq.active;

import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @description: activeMQ Queue HelloWorld
 * @author: OF3848
 * @create: 2021-08-15 22:48
 */
public class Consumer {

    /**
     * MQ地址
     */
    private static final String BROKER_URL = "tcp://127.0.0.1:61616";

    /**
     * 队列名称
     */
    private static final String QUEUE_NAME = "queue_01";

    public static void main(String[] args) throws JMSException, IOException {
        // 1、创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        // 2、获取连接
        Connection connection = connectionFactory.createConnection();
        // 3、启动连接
        connection.start();
        // 4、创建session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5、创建队列
        Queue queue = session.createQueue(QUEUE_NAME);
        // 6、创建消费者
        MessageConsumer consumer = session.createConsumer(queue);

        // // 方式1、接收消息
        // while (true){
        //     // 7、接收消息
        //     TextMessage receive = (TextMessage) consumer.receive();
        //     if(receive == null){
        //         break;
        //     }
        //     System.out.println(receive.getText());
        // }

        // 方式2、接收消息
        consumer.setMessageListener(new MessageListener() {
            @SneakyThrows
            @Override
            public void onMessage(Message message) {
                if(message != null && message instanceof TextMessage){
                    TextMessage receive = (TextMessage) message;
                    System.out.println(receive.getText());
                }
            }
        });
        // 阻塞，否则执行后续关闭代办，消息无法消费
        System.in.read();

        // 8、关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
