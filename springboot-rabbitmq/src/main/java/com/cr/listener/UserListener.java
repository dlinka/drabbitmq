package com.cr.listener;

import com.cr.enums.MessageStatus;
import com.cr.enums.Queues;
import com.cr.service.MessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class UserListener {

    @Autowired
    private MessageService messageService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = Queues.USER_QUEUE)
    public void handler(Message message, Channel channel) throws IOException {
        Integer uid = (Integer) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        Long tagId = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = (String) headers.get(PublisherCallbackChannel.RETURNED_MESSAGE_CORRELATION_KEY);

        //服务不可能宕机超过一天,所以三次重试消息不会超过一天
        //所以给一个过期时间,防止Redis空间无限增长
        String redisKey = "rabbitmq:msgId:" + msgId;
        try {
            boolean exist = stringRedisTemplate.opsForValue().setIfAbsent(redisKey, "1", 1, TimeUnit.DAYS);
            if (exist) {
                log.info("消费正在被消息 - msgId:{}", msgId);
                messageService.updateMessageStatus(msgId, MessageStatus.CONSUMED);
            } else {
                log.info("消息已经被消费了 - msgId:{}", msgId);
            }
            channel.basicAck(tagId, false);
        } catch (Exception e) {
            log.info("消息消费失败 - msgId:{}", msgId);
            stringRedisTemplate.delete(redisKey);
            //消息重新入队
            channel.basicNack(tagId, false, true);
        }
    }

}
