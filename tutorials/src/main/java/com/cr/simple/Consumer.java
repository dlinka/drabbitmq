package com.cr.simple;

import com.cr.Queue;
import com.cr.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class Consumer {

    public static void main(String[] args) {
        Connection connection = RabbitMQConnection.getInstance();
        try {
            Channel channel = connection.createChannel();
            //回调函数
            DeliverCallback deliverCallback = (consumerTag, delivery) -> System.out.println(new String(delivery.getBody()));
            channel.basicConsume(Queue.SIMPLE_QUEUE.name(), true, deliverCallback, consumerTag -> {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
