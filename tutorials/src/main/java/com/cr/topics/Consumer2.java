package com.cr.topics;

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
        channel.queueDeclare(Queue.TOPICS_QUEUE_2.name(), true, false, false, null);
        channel.queueBind(Queue.TOPICS_QUEUE_2.name(), Exchange.TOPICS_EXCHANGE.name(), "*.orange.*");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println(message);
        };
        channel.basicConsume(Queue.TOPICS_QUEUE_2.name(), true, deliverCallback, consumerTag -> { });
    }

}
