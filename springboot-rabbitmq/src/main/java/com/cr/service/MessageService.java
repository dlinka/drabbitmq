package com.cr.service;

import com.cr.entity.Message;
import com.cr.enums.MessageStatus;
import com.cr.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    public Message getMessage(String msgId){
        return messageMapper.getMessageByMsgId(msgId);
    }

    public Boolean saveMessage(Message message){
        return messageMapper.saveMessage(message);
    }

    public Boolean updateMessageStatus(String msgId, MessageStatus status){
        return messageMapper.updateMessageStatus(msgId, status.getStatus());
    }

    public Boolean updateMessageTryCount(String msgId){
        return messageMapper.updateMessageTryCount(msgId);
    }

    public List<Message> getUnConfirmMessage() {
        return messageMapper.getUnConfirmMessage();
    }
}
