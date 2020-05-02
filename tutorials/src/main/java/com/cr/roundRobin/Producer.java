package com.cr.roundRobin;

import com.cr.Queue;
import com.cr.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) {
        try (
                Connection connection = RabbitMQConnection.getInstance();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(Queue.WORK_ROUND_ROBIN_QUEUE.name(), false, false, false, null);
            for (int i = 0; i != 50; ++i) {
                String msg = "hello:" + i;
                channel.basicPublish("", Queue.WORK_ROUND_ROBIN_QUEUE.name(), null, msg.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
