package com.pjh.share.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InviteResponseDto {
    private boolean result;

    public InviteResponseDto(boolean result){
        this.result=result;
    }
}
