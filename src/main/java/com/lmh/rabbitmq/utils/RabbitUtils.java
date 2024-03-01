package com.lmh.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lmh
 * @description: 一句话描述该类的功能
 * @projectName: rabbitmq
 * @className: RabbitUtils
 * @createDate: 2024/3/1 16:45
 */
public class RabbitUtils {
    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    static {
        // ConnectionFactory用于创建MQ的物理连接
        connectionFactory.setHost("192.168.3.23");
        connectionFactory.setPort(5672); //5672是rabbitMQ的默认端口
        connectionFactory.setUsername("lmh");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/test");
    }

    public static Connection getConnection() {
        try {
            return connectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
