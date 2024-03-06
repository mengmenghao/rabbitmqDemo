package com.lmh.rabbitmq;

import com.lmh.rabbitmq.utils.RabbitConstant;
import com.lmh.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lmh
 * @description: 一句话描述该类的功能
 * @projectName: rabbitmq
 * @className: Consumer
 * @createDate: 2024/3/1 16:28
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // ConnectionFactory用于创建MQ的物理连接
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
        // 绑定队列,声明并创建一个队列，如果队列已存在，则使用这个队列
        // 参数1：队列名称，
        // 参数2：是否持久化，
        // 参数3：是否队列私有化，false代表所有消费者都可以访问，true代表只有第一次拥有它的消费者才能一直使用，其他消费者不让访问，
        // 参数4：是否自动删除，false代表不删除，代表连接停掉后不自动删除这个队列，true代表自动删除，
        // 参数5：其他额外参数，队列的其他属性
        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD, false, false, false, null);
        // 创建一个消息消费者
        // 第一个参数：队列名称
        // 第二个参数：是否自动确认收到消息，false代表手动编程来确认消息，这是MQ的推荐做法
        // 第三个参数：消费者对象，要传入DefaultConsumer实现类
        channel.basicConsume(RabbitConstant.QUEUE_HELLOWORLD,false, new Reciver(channel));
    }
}
class Reciver extends DefaultConsumer{
    private Channel channel;

    /**
     * 重写构造函数，Channel通道对象需要从外层传入，在handleDelivery方法中使用
     */
    public Reciver(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String messageBody = new String(body);
        System.out.println("消费者收到消息：" + messageBody);
        // 签收消息，确认消息
        // envelope.getDeliveryTag() 获取这个消息的TagId
        // false 只确认签收当前的消息，设置为true的时候则代表签收该消费者所有未签收的消息
        channel.basicAck(envelope.getDeliveryTag(), false);
        //super.handleDelivery(consumerTag, envelope, properties, body);
    }
}
