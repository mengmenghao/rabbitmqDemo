package com.lmh.rabbitmq.confirm;

import com.lmh.rabbitmq.utils.RabbitConstant;
import com.lmh.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Return;
import com.rabbitmq.client.ReturnCallback;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
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
        Map<String, String> area = new LinkedHashMap<>();
        area.put("china.hebei.shijiazhuang.20991011", "中河北家20991011天气数据");
        area.put("china.shandong.qingdao.20991011", "中用山东青岛20991011天i数据");
        area.put("china.henan.zhengzhou.20991011", "中同河南郑州20991011天气数据");
        area.put("us.cal.la.20991011", "美国加州洛杉矶20991011天气数据");
        area.put("china.hebei.shijiazhuang,20991012", "中中国河北家止20991012大气数据");
        area.put("china.shandong.qingdao.20991012", "中国山东青岛20991012天(数斯");
        area.put("china.henan.zhengzhou.20991012", "中国河南郑州20991012天/(数据");
        area.put("us.cal.la.20991012", "美国加州济杉矶20991012天气数据");
        Connection connection = RabbitUtils.getConnection();
        Channel channel = connection.createChannel();

        // 开启confirm监听模式
        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("消息已被Broker接收，Tag：" + deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                // 第二个参数是代表接收的数据是否为批量接收，一般我们用不到
                System.out.println("消息已被Broker拒收，Tag：" + deliveryTag);
            }
        });

        channel.addReturnListener(new ReturnCallback() {
            @Override
            public void handle(Return returnMessage) {
                System.err.println("=============================");
                System.err.println("Return编码：" + returnMessage.getReplyCode() + "-Return描述：" + returnMessage.getReplyText());
                System.err.println("交换机：" + returnMessage.getExchange() + "-路由key："+returnMessage.getRoutingKey());
                System.err.println("消息主题：" + new String(returnMessage.getBody()));
                System.err.println("=============================");
            }
        });
        Iterator<Map.Entry<String, String>> itr = area.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> me = itr.next();
            // RoutingKey 第二个参数相当于数据筛选条件
            // 第三个参数为：mandatory true代表如果消息无法正常投递则return回生产者，如果false则直接将消息丢弃
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_TOPIC, me.getKey(), true, null, me.getValue().getBytes());
        }
        //channel.close();
        //connection.close();
    }
}
