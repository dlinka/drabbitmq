package com.cr.publishSubscribe;

import com.cr.Exchange;
import com.cr.RabbitMQConnection;
import com.rabbitmq.client.BuiltinExchangeType;
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
            channel.exchangeDeclare(Exchange.PUBLISH_SUBSCRIBE_EXCHANGE.name(), BuiltinExchangeType.FANOUT);
            for (int i = 0; i < 50; i++) {
                channel.basicPublish(Exchange.PUBLISH_SUBSCRIBE_EXCHANGE.name(), "", null, ("hello" + i).getBytes());
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
