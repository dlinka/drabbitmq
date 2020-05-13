package com.cr.task;

import com.cr.entity.Message;
import com.cr.enums.Exchanges;
import com.cr.enums.MessageStatus;
import com.cr.enums.RoutingKeys;
import com.cr.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MessageTask {

    @Autowired
    private MessageService messageService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/20 * * * * ?")
    public void messageScheduled() {
        List<Message> messages = messageService.getUnConfirmMessage();
        if (messages.size() == 0) return;

        log.info("消息发送未确认个数 - count:{}", messages.size());
        messages.forEach(message -> {
            if (message.getTryCount() >= 3) {
                log.error("消息重试发送失败 - msgId:{}", message.getMsgId());
                messageService.updateMessageStatus(message.getMsgId(), MessageStatus.FAILURE);
            } else {
                log.info("消息重试发送 - msgId:{}", message.getMsgId());
                messageService.updateMessageTryCount(message.getMsgId());
                rabbitTemplate.convertAndSend(Exchanges.USER_EXCHANGE.name(), RoutingKeys.user.name(), message.getUid(), new CorrelationData(message.getMsgId()));
            }
        });
    }
}
