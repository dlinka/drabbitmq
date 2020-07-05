package com.cr.routing;

import com.cr.RabbitMQConnection;
import com.cr.constant.Exchange;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        try (
                Connection connection = RabbitMQConnection.getInstance();
                Channel channel = connection.createChannel()
        ) {
            channel.exchangeDeclare(Exchange.ROUTING_EXCHANGE.name(), BuiltinExchangeType.DIRECT);
            for (int i = 0; i < 100; i++) {
                int rk = i % 2;
                channel.basicPublish(Exchange.ROUTING_EXCHANGE.name(), rk+ "", null, ("message" + i).getBytes());
            }
        }
    }

}
