package com.pjh.share.web.dto;

import com.pjh.share.domain.group.Group;
import lombok.Getter;

@Getter
public class GroupListResponseDto {
    private Long id;
    private String title;
    private String description;
    private Integer currentNum;
    private Integer totalNum;
    private String thumbnail;
    public GroupListResponseDto(Group entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.description=entity.getDescription();
        this.currentNum=entity.getCurrentNum();
        this.totalNum=entity.getTotalNum();
        this.thumbnail=entity.getThumbnail();
    }
}
