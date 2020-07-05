package com.cr.pubsub;

import com.cr.RabbitMQConnection;
import com.cr.constant.Exchange;
import com.cr.constant.Queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class Consumer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQConnection.getInstance();
        Channel channel = connection.createChannel();
        channel.queueDeclare(Queue.PUBLISH_SUBSCRIBE_QUEUE_2.name(), true, false, false, null);
        channel.queueBind(Queue.PUBLISH_SUBSCRIBE_QUEUE_2.name(), Exchange.PUBLISH_SUBSCRIBE_EXCHANGE.name(), "");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println(message);
        };
        channel.basicConsume(Queue.PUBLISH_SUBSCRIBE_QUEUE_2.name(), true, deliverCallback, consumerTag -> { });
    }

}
