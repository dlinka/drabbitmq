package com.cr.routing;

import com.cr.Exchange;
import com.cr.Queue;
import com.cr.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class Consumer2 {
    public static void main(String[] args) {
        Connection connection = RabbitMQConnection.getInstance();
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(Queue.ROUTING_QUEUE_2.name(), false, false, true, null);
            channel.queueBind(Queue.ROUTING_QUEUE_2.name(), Exchange.ROUTING_EXCHANGE.name(), "routing key:false");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> System.out.println(new String(delivery.getBody()));
            channel.basicConsume(Queue.ROUTING_QUEUE_2.name(), true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
