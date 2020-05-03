package com.cr.transaction;

import com.cr.Queue;
import com.cr.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) {

        try (
                Connection instance = RabbitMQConnection.getInstance();
                Channel channel = instance.createChannel()
        ) {
            channel.queueDeclare(Queue.TRANSACTION_QUEUE.name(), false, false, false, null);
            try {
                channel.txSelect();
                channel.basicPublish("", Queue.TRANSACTION_QUEUE.name(), null, "hello".getBytes());
                int j = 1 / 0;
                channel.basicPublish("", Queue.TRANSACTION_QUEUE.name(), null, "world".getBytes());
                channel.txCommit();
            } catch (Exception e) {
                channel.txRollback();
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
