package com.cr.roundRobin;

import com.cr.Queue;
import com.cr.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Consumer1 {

    public static void main(String[] args) {
        Connection connection = RabbitMQConnection.getInstance();
        try {
            Channel channel = connection.createChannel();
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("Consumer1 : " + new String(delivery.getBody()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            channel.basicConsume(Queue.WORK_ROUND_ROBIN_QUEUE.name(), true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
