package com.pjh.share.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InviteRequestDto {
    private String name;

    public InviteRequestDto(String name){
        this.name=name;
    }
}
