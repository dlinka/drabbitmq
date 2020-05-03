package com.cr.confirm;

import com.cr.Queue;
import com.cr.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConfirmMult {

    public static void main(String[] args) {
        try (

                Connection connection = RabbitMQConnection.getInstance();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(Queue.CONFIRM_MULT_QUEUE.name(), false, false, false, null);
            //confirm模式
            channel.confirmSelect();
            for (int i = 0; i < 10000; i++) {
                channel.basicPublish("", Queue.CONFIRM_MULT_QUEUE.name(), null, "hello".getBytes());
            }
            if (channel.waitForConfirms()) {
                System.out.println("messages send success");
            } else {
                System.out.println("messages send fail");
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
