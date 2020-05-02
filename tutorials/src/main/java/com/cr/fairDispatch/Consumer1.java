package com.cr.fairDispatch;

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
            //每次只发送一个消息给消费者
            channel.basicQos(1);
            //回调函数
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

            boolean ack = false; //消息应答
            channel.basicConsume(Queue.WORK_FAIR_DISPATCH_QUEUE.name(), ack, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
