package com.cr.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Message {

    private String msgId;
    private Integer uid;
    private String exchange;
    private String routingKey;
    private Byte status;
    private Integer tryCount;
    private LocalDateTime tryTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
