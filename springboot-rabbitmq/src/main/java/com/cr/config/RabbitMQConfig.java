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
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Slf4j
@Configuration
public class RabbitMQConfig {

    @Autowired
    private MessageService messageService;

    @Bean
    DirectExchange userExchange(){
        return new DirectExchange(Exchanges.USER_EXCHANGE);
    }

    @Bean
    Queue userQueue(){
        return new Queue(Queues.USER_QUEUE);
    }

    @Bean
    Binding emailBind(){
        return BindingBuilder.bind(userQueue()).to(userExchange()).with(RoutingKeys.USER);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //消息发送到交换机确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            String msgId = correlationData.getId();
            if (ack) {
                log.info("消息发送到交换机成功 - msgId:{}", msgId);
                messageService.updateMessageStatus(msgId, MessageStatus.CONFIRM);
            } else {
                log.error("消息发送交换机失败 - msgId:{}, reason:{}", msgId, cause);
            }
        });
        //消息从交换机路由到队列回调(只有失败的回调)
        //生产环境建议把这个去掉,一方面这个失败率低,一方面关闭这个功能可以提高性能
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            String msgId = (String) headers.get(PublisherCallbackChannel.RETURNED_MESSAGE_CORRELATION_KEY);
            log.error("消息路由失败 - msgId:{}, replyCode:{}, replyText:{}, exchange:{}, routingKey:{}", msgId, replyCode, replyText, exchange, routingKey);
        });
        return rabbitTemplate;
    }

}
