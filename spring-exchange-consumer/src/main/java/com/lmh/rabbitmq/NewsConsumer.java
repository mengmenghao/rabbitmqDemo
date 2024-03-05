package com.lmh.rabbitmq;

import com.lmh.rabbit.exchange.News;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lmh
 * @description: 一句话描述该类的功能
 * @projectName: rabbitmq
 * @className: NewsConsumer
 * @createDate: 2024/3/5 20:56
 */
public class NewsConsumer {

    public void recv(News news) {
        System.out.println("接收到最新新闻：" + news.getTitle() + ":" + news.getSource());
    }

    public static void main(String[] args) {
        // 初始化ioc容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }
}
