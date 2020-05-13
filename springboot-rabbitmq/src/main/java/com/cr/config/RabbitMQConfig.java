package com.cr.config;

import com.cr.enums.Exchanges;
import com.cr.enums.MessageStatus;
import com.cr.enums.Queues;
import com.cr.enums.RoutingKeys;
import com.cr.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMQConfig {

    @Autowired
    private MessageService messageService;

    @Bean
    DirectExchange userExchange(){
        return new DirectExchange(Exchanges.USER_EXCHANGE.name());
    }

    @Bean
    Queue userQueue(){
        return new Queue(Queues.USER_QUEUE.name());
    }

    @Bean
    Binding emailBind(){
        return BindingBuilder.bind(userQueue()).to(userExchange()).with(RoutingKeys.user.name());
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        //消息确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            String msgId = correlationData.getId();
            if (ack) {
                log.info("消息发送成功 - msgId:{}", msgId);
                messageService.updateMessageStatus(msgId, MessageStatus.CONFIRM);
            } else {
                log.error("消息发送失败 - msgId:{}, cause:{}", msgId, cause);
            }
        });

        //不可路由的消息的回调配置
        //只会通知失败,不会通知成功
        //生产环境可以把这个去掉,因为这个失败的概率很低,而且关闭这个功能可以提高性能
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String messageId = message.getMessageProperties().getMessageId();
            log.error("消息路由失败 - id:{}, replyCode:{}, replyText:{}, exchange:{}, routingKey:{}", messageId, replyCode, replyText, exchange, routingKey);
        });

        return rabbitTemplate;
    }

}
