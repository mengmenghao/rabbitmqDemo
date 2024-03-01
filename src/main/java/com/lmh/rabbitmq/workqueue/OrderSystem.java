package com.lmh.rabbitmq.workqueue;

import com.google.gson.Gson;
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
 * @className: OrderSystem
 * @createDate: 2024/3/1 17:38
 */
public class OrderSystem {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_SMS, false, false, false, null);
        for (int i = 100; i < 200; i++) {
            SMS sms = new SMS("乘客" + i, "13900000" + i, "您的车票已预订成功");
            String jsonSMS = new Gson().toJson(sms);
            channel.basicPublish("", RabbitConstant.QUEUE_SMS, null, jsonSMS.getBytes());
        }
        channel.close();
        connection.close();
        System.out.println("短信发送成功");
    }
}
