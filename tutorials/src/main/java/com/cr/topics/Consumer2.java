package com.cr.topics;

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
            channel.queueDeclare(Queue.TOPICS_QUEUE_2.name(), false, false, false, null);
            channel.queueBind(Queue.TOPICS_QUEUE_2.name(), Exchange.TOPICS_EXCHANGE.name(), "2.#");
            channel.queueBind(Queue.TOPICS_QUEUE_2.name(), Exchange.TOPICS_EXCHANGE.name(), "4.#");
            DeliverCallback deliverCallback = (consumerTag, delivery) -> System.out.println(new String(delivery.getBody()));
            channel.basicConsume(Queue.TOPICS_QUEUE_2.name(), true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
