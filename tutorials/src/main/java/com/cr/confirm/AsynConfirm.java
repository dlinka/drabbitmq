package com.cr.confirm;

import com.cr.Queue;
import com.cr.RabbitMQConnection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class AsynConfirm {

    public static SortedSet<Long> seqNos = Collections.synchronizedSortedSet(new TreeSet<>());

    public static void main(String[] args) {
        try (
                Connection connection = RabbitMQConnection.getInstance();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(Queue.ASYN_CONFIRM_QUEUE.name(), false, false, false, null);
            channel.confirmSelect();
            //添加监听
            channel.addConfirmListener(new ConfirmListener() {
                //发送成功
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println(String.format("发送成功, multiple:%b, deliverTag:%d", multiple, deliveryTag));
                    if (multiple) {
                        seqNos.headSet(deliveryTag + 1).clear();
                    } else {
                        seqNos.remove(deliveryTag);
                    }
                }

                //发送失败
                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println(String.format("发送失败, multiple:%b, deliverTag:%d", multiple, deliveryTag));
                    if (multiple) {
                        seqNos.headSet(deliveryTag + 1).clear();
                    } else {
                        seqNos.remove(deliveryTag);
                    }
                }
            });
            while (true) {
                //获取下一个序列号
                long seqNo = channel.getNextPublishSeqNo();
                channel.basicPublish("", Queue.ASYN_CONFIRM_QUEUE.name(), null, "hello".getBytes());
                seqNos.add(seqNo);
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
