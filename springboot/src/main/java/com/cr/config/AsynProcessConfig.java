package com.cr.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AsynProcessConfig {

    @Bean
    public Queue emailQueue(){
        return new Queue("email_queue");
    }

    @Bean
    public Queue messageQueue(){
        return new Queue("message_queue");
    }

    @Bean
    public FanoutExchange userRegisterSuccessExchange(){
        return new FanoutExchange("user_register_success_exchange");
    }

    @Bean
    public Binding bindingEmailQueue(Queue emailQueue, FanoutExchange userRegisterSuccessExchange){
        return BindingBuilder.bind(emailQueue).to(userRegisterSuccessExchange);
    }

    @Bean
    public Binding bindingMessageQueue(Queue messageQueue, FanoutExchange userRegisterSuccessExchange){
        return BindingBuilder.bind(messageQueue).to(userRegisterSuccessExchange);
    }

}
