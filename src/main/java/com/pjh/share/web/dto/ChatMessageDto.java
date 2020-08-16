package com.pjh.share.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ChatMessageDto implements Serializable {
    private String roomKey;
    private String sender;
    private String content;
    private String type;

    public ChatMessageDto(String roomKey,String type,String sender,String content){
        this.roomKey=roomKey;
        this.type=type;
        this.sender=sender;
        this.content=content;
    }
}
