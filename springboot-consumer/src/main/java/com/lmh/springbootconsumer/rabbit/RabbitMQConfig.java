package com.lmh.springbootconsumer.rabbit;

import ch.qos.logback.classic.pattern.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lmh
 * @description: 一句话描述该类的功能
 * @projectName: rabbitmq
 * @className: RabbitMQConfig
 * @createDate: 2024/3/6 15:31
 */
@Configuration
public class RabbitMQConfig {
    //@Bean
    //public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
    //    return new Jackson2JsonMessageConverter(objectMapper);
    //}
}
