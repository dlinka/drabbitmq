package com.cr.service;

import com.cr.entity.Message;
import com.cr.entity.User;
import com.cr.enums.Exchanges;
import com.cr.enums.MessageStatus;
import com.cr.enums.RoutingKeys;
import com.cr.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public User getUser(Integer uid) {
        User user = userMapper.getUserByUid(uid);
        UUID uuid = UUID.randomUUID();
        messageService.saveMessage(Message.builder().msgId(uuid.toString()).uid(user.getUid()).exchange(Exchanges.USER_EXCHANGE).routingKey(RoutingKeys.USER).status(MessageStatus.UNCONFIRM.getStatus()).tryTime(LocalDateTime.now().plusMinutes(1)).build());
        log.info("发送消息 - msgId:{}", uuid.toString());
        rabbitTemplate.convertAndSend(Exchanges.USER_EXCHANGE, RoutingKeys.USER, user.getUid(), new CorrelationData(uuid.toString()));
        rabbitTemplate.convertAndSend(Exchanges.USER_EXCHANGE, RoutingKeys.USER, user.getUid(), new CorrelationData(uuid.toString()));
        rabbitTemplate.convertAndSend(Exchanges.USER_EXCHANGE, RoutingKeys.USER, user.getUid(), new CorrelationData(uuid.toString()));
        rabbitTemplate.convertAndSend(Exchanges.USER_EXCHANGE, RoutingKeys.USER, user.getUid(), new CorrelationData(uuid.toString()));
        return user;
    }

}
