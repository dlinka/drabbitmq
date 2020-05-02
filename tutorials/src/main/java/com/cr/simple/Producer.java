package com.cr.simple;

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
            //声明队列
            channel.queueDeclare(Queue.SIMPLE_QUEUE.name(), false, false, false, null);
            //发送消息
            channel.basicPublish("", Queue.SIMPLE_QUEUE.name(), null, "hello".getBytes());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
