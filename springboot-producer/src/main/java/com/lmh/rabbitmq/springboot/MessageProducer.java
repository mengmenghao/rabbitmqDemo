package com.lmh.rabbitmq.springboot;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author lmh
 * @description: 一句话描述该类的功能
 * @projectName: rabbitmq
 * @className: MessageProducer
 * @createDate: 2024/3/5 22:34
 */
@Component
public class MessageProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    // correlationData:消息的附加信息，即自定义id；isack:代表消息是否被broker（mq）接收，true代表接收；cause 如果拒收cause则说明拒收的原因，帮助我们进行后续处理
    RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, isAck, cause) -> {
        System.out.println("===============================");
        System.out.println(correlationData);
        System.out.println("ack:" + isAck);
        if (!isAck) {
            System.out.println(cause);
        }
    };

    RabbitTemplate.ReturnsCallback returnsCallback = (returnedMessage) -> {
        System.out.println("code:" + returnedMessage.getReplyCode() + ",text:" + returnedMessage.getReplyText());
        System.out.println("exchange:" + returnedMessage.getExchange() + ",routingkey" + returnedMessage.getRoutingKey());
    };

    public void sendMsg(Employee employee) {
        // CorrelationData对象的作用是作为消息的附加消息传递，通常我们用它老保存消息的自定义id
        CorrelationData correlationData = new CorrelationData(employee.getEmpno() + "-" + new Date().getTime());
        //rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnsCallback(returnsCallback);
        rabbitTemplate.convertAndSend("springboot-exchange", "hr.employee", employee, correlationData);
    }
}
