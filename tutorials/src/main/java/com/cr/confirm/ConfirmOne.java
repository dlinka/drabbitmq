package com.cr.confirm;

import com.cr.Queue;
import com.cr.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmOne {

    public static void main(String[] args) {
        try (

                Connection connection = RabbitMQConnection.getInstance();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(Queue.CONFIRM_ONE_QUEUE.name(), false, false, false, null);
            //confirm模式
            channel.confirmSelect();
            channel.basicPublish("", Queue.CONFIRM_ONE_QUEUE.name(), null, "hello".getBytes());
            if (channel.waitForConfirms()) {
                System.out.println("message send success");
            } else {
                System.out.println("message send fail");
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
