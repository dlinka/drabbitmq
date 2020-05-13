# 🐰🐇drabbitmq

## AMQP源码解析

[AMQP源码解析一-CachingConnectionFactory](https://github.com/dlinka/drabbitmq/issues/6)

[AMQP源码解析二-RabbitAdmin](https://github.com/dlinka/drabbitmq/issues/7)

[AMQP源码解析三-RabbitTemplate](https://github.com/dlinka/drabbitmq/issues/8)

[AMQP源码解析四-连接的创建](https://github.com/dlinka/drabbitmq/issues/9)

[AMQP源码解析五-信道的创建](https://github.com/dlinka/drabbitmq/issues/10)

[AMQP源码解析六-信道和连接关闭](https://github.com/dlinka/drabbitmq/issues/11)

## Spring Boot整合RabbitMQ

    Module:springboot-rabbitmq

    案例描述:
    利用RabbitMQ实现异步处理场景
    
    技术点:
    RabbitMQ的订阅/发布模型:DirectExchange
    RabbitMQ生产者消息确认:publisher-confirms(发送到交换机确认),publisher-returns(路由到队列确认)
    定时任务:@EnableScheduling,@Scheduled,cron表达式
    RabbitMQ消费者消息应答:@RabbitListener,listener.simple.acknowledge-mode=manual
    幂等性:Redis

---
        
