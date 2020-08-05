package com.pjh.share.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomInviteResponseDto {
    private boolean result;
    public ChatRoomInviteResponseDto(boolean result){
        this.result=result;
    }
}
