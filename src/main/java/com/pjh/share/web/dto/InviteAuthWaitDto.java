package com.pjh.share.web.dto;

import com.pjh.share.domain.friend.InviteAuthWait;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InviteAuthWaitDto {
    private Long id;
    private String name;
    private String state;


    public InviteAuthWaitDto(InviteAuthWait entity){
        this.id=entity.getId();
        this.name=entity.getSender();
        this.state=entity.getState().getValue();
    }
}
