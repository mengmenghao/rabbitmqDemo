package com.lmh.rabbitmq;

import com.lmh.rabbitmq.utils.RabbitConstant;
import com.lmh.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lmh
 * @description: 一句话描述该类的功能
 * @projectName: rabbitmq
 * @className: Producer
 * @createDate: 2024/3/1 16:04
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //// ConnectionFactory用于创建MQ的物理连接
        //ConnectionFactory connectionFactory = new ConnectionFactory();
        //connectionFactory.setHost("192.168.3.23");
        //connectionFactory.setPort(5672); //5672是rabbitMQ的默认端口
        //connectionFactory.setUsername("lmh");
        //connectionFactory.setPassword("123456");
        //connectionFactory.setVirtualHost("/test");
        // tcp物理连接
        Connection connection = RabbitUtils.getConnection();
        // 创建通信通道，向导鱼TCP中的虚拟连接
        Channel channel = connection.createChannel();
        // 创建队列,声明并创建一个队列，如果队列已存在，则使用这个队列
        // 参数1：队列名称，
        // 参数2：是否持久化，
        // 参数3：是否队列私有化，false代表所有消费者都可以访问，true代表只有第一次拥有它的消费者才能一直使用，其他消费者不让访问，
        // 参数4：是否自动删除，false代表不删除，代表连接停掉后不自动删除这个队列，true代表自动删除，
        // 参数5：其他额外参数，队列的其他属性
        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD, false, false, false, null);
        // 四个参数
        //exchange 交换机,暂时用不到，在后面进行发布订阅时才会用到
        // 队列名称
        // 额外的设置属性
        // 最后一个参数是要传递的消息字节数组
        String message = "helloworld!@";
        channel.basicPublish("",RabbitConstant.QUEUE_HELLOWORLD,null,message.getBytes());
        channel.close();
        connection.close();
        System.out.println("消息发送成功");
    }
}
