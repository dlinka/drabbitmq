package com.cr.routing;

import com.cr.RabbitMQConnection;
import com.cr.constant.Exchange;
import com.cr.constant.Queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class Consumer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQConnection.getInstance();
        Channel channel = connection.createChannel();
        channel.queueDeclare(Queue.ROUTING_QUEUE_1.name(), true, false, false, null);
        channel.queueBind(Queue.ROUTING_QUEUE_1.name(), Exchange.ROUTING_EXCHANGE.name(), "0");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println(message);
        };
        channel.basicConsume(Queue.ROUTING_QUEUE_1.name(), true, deliverCallback, consumerTag -> { });
    }

}
