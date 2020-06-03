package com.pjh.share.web.dto;

import com.pjh.share.domain.groupaccount.GroupAccount;
import com.pjh.share.domain.groupaccount.Role;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class GroupMemberListResponseDto {
    private String name;
    private String role;
    private String modifiedDate;


    public GroupMemberListResponseDto(GroupAccount entity){
        this.name=entity.getAccount().getName();
        this.role=entity.getRole().getTitle();
        this.modifiedDate=entity.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
