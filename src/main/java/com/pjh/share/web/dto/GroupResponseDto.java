package com.pjh.share.web.dto;

import com.pjh.share.domain.group.Group;
import lombok.Getter;

@Getter
public class GroupResponseDto {
    private Long id;
    private String title;
    private String description;
    private String groupAdmin;
    private Integer totalNum;
    private Integer currentNum;

    public GroupResponseDto(Group entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.description=entity.getDescription();
        this.groupAdmin=entity.getAccount().getName();
        this.totalNum=entity.getTotalNum();
        this.currentNum=entity.getCurrentNum();
    }
}
