package com.jianghu.mq.active;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;
import javax.jms.Topic;
import java.util.UUID;

/**
 * @description:
 * @author: OF3848
 * @create: 2021-08-17 23:06
 */
@Service
public class ActiveMqService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    /**
     * 发送到Queue
     */
    public void sendMessageQueue() {
        jmsMessagingTemplate.convertAndSend(queue, "Queue----" + UUID.randomUUID().toString());
    }

    /**
     * 发送到Topic
     */
    public void sendMessageTopic() {
        jmsMessagingTemplate.convertAndSend(topic, "Topic----" + UUID.randomUUID().toString());
    }
}
