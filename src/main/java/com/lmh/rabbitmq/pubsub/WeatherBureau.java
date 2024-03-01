package com.lmh.rabbitmq.pubsub;

import com.lmh.rabbitmq.utils.RabbitConstant;
import com.lmh.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lmh
 * @description: 一句话描述该类的功能
 * @projectName: rabbitmq
 * @className: WeatherBureau
 * @createDate: 2024/3/1 18:09
 */
public class WeatherBureau {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER,"",null,"天气测试信息".getBytes());
        channel.close();
        connection.close();
    }
}
