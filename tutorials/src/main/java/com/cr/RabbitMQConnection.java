package com.cr;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConnection {

    private static volatile Connection connection;

    private RabbitMQConnection() {
    }

    public static Connection getInstance() {
        if (connection == null) {
            synchronized (RabbitMQConnection.class) {
                if (connection == null) {
                    ConnectionFactory factory = new ConnectionFactory();
                    factory.setHost("127.0.0.1");
                    factory.setPort(5672);
                    factory.setUsername("cr");
                    factory.setPassword("123456");
                    factory.setVirtualHost("/tutorials");
                    Connection connection = null;
                    try {
                        connection = factory.newConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                    return connection;
                }
            }
        }
        return connection;
    }

}
