package com.cr.work;

import com.cr.RabbitMQConnection;
import com.cr.constant.Queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Consumer1 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQConnection.getInstance();
        Channel channel = connection.createChannel();

        //每次只发送一个消息给消费者
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Consumer1 : " + new String(delivery.getBody()));

                //消息应答
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        //消息应答
        boolean ack = false;
        channel.basicConsume(Queue.WORK_QUEUE.name(), ack, deliverCallback, consumerTag -> { });
    }

}
