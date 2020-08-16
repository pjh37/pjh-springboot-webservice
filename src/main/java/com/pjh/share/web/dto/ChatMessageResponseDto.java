package com.pjh.share.web.dto;

import com.pjh.share.domain.message.Message;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ChatMessageResponseDto {
    private String roomKey;
    private String name;
    private String content;
    private String createdDate;
    public ChatMessageResponseDto(Message entity){
        this.roomKey=entity.getChatRoom().getRoomKey();
        this.name=entity.getName();
        this.content=entity.getContent();
        this.createdDate=entity.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
