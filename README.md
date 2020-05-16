# 🐰🐇drabbitmq

# AMQP源码解析

* [AMQP源码解析一-CachingConnectionFactory](https://github.com/dlinka/drabbitmq/issues/6)
* [AMQP源码解析二-RabbitAdmin](https://github.com/dlinka/drabbitmq/issues/7)
* [AMQP源码解析三-RabbitTemplate](https://github.com/dlinka/drabbitmq/issues/8)
* [AMQP源码解析四-连接的创建](https://github.com/dlinka/drabbitmq/issues/9)
* [AMQP源码解析五-信道的创建](https://github.com/dlinka/drabbitmq/issues/10)
* [AMQP源码解析六-信道和连接关闭](https://github.com/dlinka/drabbitmq/issues/11)

# Spring Boot整合RabbitMQ

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

# 联系我们

如果我的博客中有任何问题,可以通过Issues给我留言,我会第一时间回复,请你喝哈啤🍺🍺🍺

如果你想学习技术💰💰💰,或者你有什么好的idea💡💡💡,可以加微信与我们探讨🐒🐒🐒

<img width="150" height="150" src="https://user-images.githubusercontent.com/4274041/82111702-89fb3480-9779-11ea-97a9-c1ee1ee4e7be.png"/>
