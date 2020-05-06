package com.cr.asynProcess;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "email_queue")
public class SendEmail {

    @RabbitHandler
    public void process(String userId) {
        System.out.println(userId + "发送邮件...");
    }

}
