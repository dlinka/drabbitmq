package com.cr.topics;

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
            channel.exchangeDeclare(Exchange.TOPICS_EXCHANGE.name(), BuiltinExchangeType.TOPIC);
            channel.basicPublish(Exchange.TOPICS_EXCHANGE.name(), "quick.orange.fox", null, ("quick.orange.rabbit").getBytes());
        }
    }
}
