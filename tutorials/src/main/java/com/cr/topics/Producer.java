package com.cr.topics;

import com.cr.Exchange;
import com.cr.RabbitMQConnection;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) {
        try (
                Connection connection = RabbitMQConnection.getInstance();
                Channel channel = connection.createChannel()
        ) {
            channel.exchangeDeclare(Exchange.TOPICS_EXCHANGE.name(), BuiltinExchangeType.TOPIC);
            Random random = new Random();
            for (int i = 0; i < 50; i++) {
                int r = random.nextInt(5);
                channel.basicPublish(Exchange.TOPICS_EXCHANGE.name(), (r + ".topics"), null, (r + " " + i).getBytes());
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
