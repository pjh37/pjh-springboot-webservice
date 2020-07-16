package com.pjh.share.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateResponseDto {
    private String roomKey;

    public ChatRoomCreateResponseDto(String roomKey){
        this.roomKey=roomKey;
    }
}
