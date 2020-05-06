package com.cr.asynProcess;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "message_queue")
public class SendMessage {

    @RabbitHandler
    public void process(String userId) {
        System.out.println(userId + "发送短信...");
    }

}
