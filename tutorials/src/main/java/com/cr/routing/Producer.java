package com.cr.routing;

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
            channel.exchangeDeclare(Exchange.ROUTING_EXCHANGE.name(), BuiltinExchangeType.DIRECT);
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                channel.basicPublish(Exchange.ROUTING_EXCHANGE.name(), "routing key:" + random.nextBoolean(), null, ("hello" + i).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
