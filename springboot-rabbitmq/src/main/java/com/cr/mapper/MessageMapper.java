package com.cr.mapper;

import com.cr.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {

    Boolean saveMessage(Message message);
    Message getMessageByMsgId(String msgId);
    Boolean updateMessageStatus(@Param("msgId") String msgId, @Param("status") Byte status);
    Boolean updateMessageTryCount(String msgId);
    List<Message> getUnConfirmMessage();
}
