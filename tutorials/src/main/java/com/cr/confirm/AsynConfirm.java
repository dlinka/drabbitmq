package com.cr.confirm;

import com.cr.RabbitMQConnection;
import com.cr.constant.Queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.*;

public class AsynConfirm {

    static ConcurrentSkipListMap<Long, String> notConfirmed = new ConcurrentSkipListMap<>();
    static ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 10000; i++) {
            messageQueue.add(UUID.randomUUID().toString());
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        try (
                Connection connection = RabbitMQConnection.getInstance();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(Queue.ASYN_CONFIRM_QUEUE.name(), false, false, false, null);
            channel.confirmSelect();

            //消息正确送达的消息回调
            ConfirmCallback confirmCallback = (sequenceNumber, multiple) -> {
                //multiple等于true代表多个消息确认
                //multiple等于false代表一个消息确认
                if (multiple) {
                    ConcurrentNavigableMap<Long, String> confirmed = notConfirmed.headMap(sequenceNumber, true);
                    confirmed.clear();
                } else {
                    notConfirmed.remove(sequenceNumber);
                }
                System.out.println("sequenceNumber:" + sequenceNumber + ", notConfirmedSize:" + notConfirmed.size());
            };

            ConfirmCallback noConfirmCallback = (sequenceNumber, multiple) -> {
                System.err.format("no confirm callback sequenceNumber:%d, multiple:%b %n", sequenceNumber, multiple);
                ConcurrentNavigableMap<Long, String> notConfirm = notConfirmed.headMap(sequenceNumber, true);
                for (String value : notConfirm.values()) {
                    messageQueue.add(value);
                    System.err.format("消息重新入队:%s", value);
                }
                //同时把未确认的消息从Map中删除
                notConfirm.clear();
            };

            channel.addConfirmListener(confirmCallback, noConfirmCallback);

            String message = null;
            while ((message = messageQueue.poll()) != null) {
                //获取下一个序列号
                long seqNo = channel.getNextPublishSeqNo();
                notConfirmed.put(seqNo, message);
                channel.basicPublish("", Queue.ASYN_CONFIRM_QUEUE.name(), null, message.getBytes());
            }
            TimeUnit.SECONDS.sleep(60);
        }
    }
}
