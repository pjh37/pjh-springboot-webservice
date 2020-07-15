package com.pjh.share.web.dto;

import com.pjh.share.domain.chatroom.ChatRoom;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateDto {
    private String title;

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .title(title)
                .build();
    }
}
