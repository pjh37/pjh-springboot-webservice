package com.pjh.share.web.dto;

import com.pjh.share.domain.chatroom.ChatRoom;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ChatRoomCreateDto {
    private String title;

    public ChatRoom toEntity(){
        String roomKey= UUID.randomUUID().toString().replaceAll("-","");
        return ChatRoom.builder()
                .title(title)
                .roomKey(roomKey)
                .build();
    }
}
