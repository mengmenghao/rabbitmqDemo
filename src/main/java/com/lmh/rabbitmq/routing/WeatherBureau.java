package com.lmh.rabbitmq.routing;

import com.lmh.rabbitmq.utils.RabbitConstant;
import com.lmh.rabbitmq.utils.RabbitUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

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
        Iterator<Map.Entry<String,String>> itr = area.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String,String> me = itr.next();
            // RoutingKey 第二个参数相当于数据筛选条件
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_ROUTING, me.getKey(), null, me.getValue().getBytes());
        }
        channel.close();
        connection.close();
    }
}
