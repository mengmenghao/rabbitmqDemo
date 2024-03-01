package com.lmh.rabbitmq.routing;

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
 * @className: Baidu
 * @createDate: 2024/3/1 18:13
 */
public class Sina {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_SINA, false, false, false, null);
        // queueBind 用于将队列与交换机绑定
        // 参数1：队列名 参数2：交换机名 参数3：路由键
        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER_ROUTING, "us.cal.la.20991011");
        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER_ROUTING, "china.henan.zhengzhou.20991011");
        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER_ROUTING, "us.cal.la.20991012");
        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER_ROUTING, "china.henan.zhengzhou.20991012");

        channel.basicQos(1);
        channel.basicConsume(RabbitConstant.QUEUE_SINA,false,new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("Sina收到气象消息：" + message);
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
