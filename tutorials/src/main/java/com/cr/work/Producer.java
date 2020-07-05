package com.cr.work;

import com.cr.RabbitMQConnection;
import com.cr.constant.Queue;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        try (
                Connection connection = RabbitMQConnection.getInstance();
                Channel channel = connection.createChannel()
        ) {
            //队列持久化
            boolean durable = true;
            channel.queueDeclare(Queue.WORK_QUEUE.name(), durable, false, false, null);

            for (int i = 0; i != 50; ++i) {
                String message = "message:" + i;

                //消息持久化
                AMQP.BasicProperties persistentTextPlain = MessageProperties.PERSISTENT_TEXT_PLAIN;
                channel.basicPublish("", Queue.WORK_QUEUE.name(), persistentTextPlain, message.getBytes());
            }
        }
    }

}
