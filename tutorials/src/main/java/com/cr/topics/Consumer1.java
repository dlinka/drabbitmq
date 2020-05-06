package com.cr.topics;

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
            channel.queueDeclare(Queue.TOPICS_QUEUE_1.name(), false, false, false, null);
            channel.queueBind(Queue.TOPICS_QUEUE_1.name(), Exchange.TOPICS_EXCHANGE.name(), "1.#");
            channel.queueBind(Queue.TOPICS_QUEUE_1.name(), Exchange.TOPICS_EXCHANGE.name(), "3.#");
            channel.basicConsume(Queue.TOPICS_QUEUE_1.name(), true, (consumerTag, delivery) -> System.out.println(new String(delivery.getBody())), consumerTag -> {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
