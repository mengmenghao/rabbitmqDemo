package com.lmh.rabbitmq.workqueue;

import com.lmh.rabbitmq.utils.RabbitConstant;
import com.lmh.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author lmh
 * @description: 一句话描述该类的功能
 * @projectName: rabbitmq
 * @className: SMSSender
 * @createDate: 2024/3/1 17:46
 */
public class SMSSender3 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_SMS, false, false, false, null);
        // 如果不写basicQos(1)，则自动MQ会将所有请求平均发送给所有消费者
        // basicQos，MQ不再对消费者一次发送多个请求，而是消费者请求处理完一个消息后（确认后），再从队列获取一个新的
        channel.basicQos(1);
        channel.basicConsume(RabbitConstant.QUEUE_SMS,false,new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String jsonSMS = new String(body);
                System.out.println("SMSSender3-短信发送成功：" + jsonSMS);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
