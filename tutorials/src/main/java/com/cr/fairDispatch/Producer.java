package com.cr.fairDispatch;

import com.cr.Queue;
import com.cr.RabbitMQConnection;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) {
        try (
                Connection connection = RabbitMQConnection.getInstance();
                Channel channel = connection.createChannel()
        ) {
            boolean durable = true; //队列持久化
            channel.queueDeclare(Queue.WORK_FAIR_DISPATCH_QUEUE.name(), durable, false, false, null);

            StringBuffer sb = new StringBuffer("hello ");
            for (int i = 0; i != 50; ++i) {
                sb.append(i);
                //消息持久化
                AMQP.BasicProperties persistentTextPlain = MessageProperties.PERSISTENT_TEXT_PLAIN;
                channel.basicPublish("", Queue.WORK_FAIR_DISPATCH_QUEUE.name(), persistentTextPlain, sb.toString().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
