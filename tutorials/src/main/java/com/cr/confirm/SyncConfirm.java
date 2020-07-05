package com.cr.confirm;

import com.cr.RabbitMQConnection;
import com.cr.constant.Queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SyncConfirm {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        publishMessagesIndividually();
        publishMessagesInBatch();
    }

    static void publishMessagesIndividually() throws IOException, TimeoutException, InterruptedException {
        try (
                Connection connection = RabbitMQConnection.getInstance();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(Queue.CONFIRM_ONE_QUEUE.name(), true, false, false, null);
            channel.confirmSelect();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                String message = "message" + i;
                channel.basicPublish("", Queue.CONFIRM_ONE_QUEUE.name(), null, message.getBytes());
                channel.waitForConfirmsOrDie(5_000);
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }
    }

    static void publishMessagesInBatch() throws IOException, TimeoutException, InterruptedException {
        try (
                Connection connection = RabbitMQConnection.getInstance();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(Queue.CONFIRM_MULT_QUEUE.name(), true, false, false, null);
            channel.confirmSelect();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                String message = "message" + i;
                channel.basicPublish("", Queue.CONFIRM_MULT_QUEUE.name(), null, message.getBytes());
                if (i % 500 == 0) {
                    channel.waitForConfirmsOrDie(5_000);
                }
            }
            channel.waitForConfirmsOrDie(5_000);
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }
    }

}
