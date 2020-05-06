package com.cr.publishSubscribe;

import com.cr.Exchange;
import com.cr.Queue;
import com.cr.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class Consumer1 {
    public static void main(String[] args) {
        Connection connection = RabbitMQConnection.getInstance();
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(Queue.PUBLISH_SUBSCRIBE_QUEUE_1.name(), false, false, true, null);
            channel.queueBind(Queue.PUBLISH_SUBSCRIBE_QUEUE_1.name(), Exchange.PUBLISH_SUBSCRIBE_EXCHANGE.name(), "");
            channel.basicConsume(Queue.PUBLISH_SUBSCRIBE_QUEUE_1.name(), true, (consumerTag, delivery) -> System.out.println(new String(delivery.getBody())), consumerTag -> {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
