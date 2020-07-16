package com.pjh.share.web.dto;

import com.pjh.share.domain.chatroom.ChatRoom;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ChatRoomListResponseDto {
    private String title;
    private String createdDate;
    public ChatRoomListResponseDto(ChatRoom chatRoom){
        this.title=chatRoom.getTitle();
        this.createdDate=chatRoom.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }
}
