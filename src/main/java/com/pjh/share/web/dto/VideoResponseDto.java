package com.pjh.share.web.dto;


import com.pjh.share.domain.video.Video;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class VideoResponseDto {
    private Long id;
    private Long clickCount;
    private String name;
    private String title;
    private String fileName;
    private LocalDateTime modifiedDate;
    public VideoResponseDto(Video entity){
        this.id=entity.getId();
        this.name=entity.getName();
        this.clickCount=entity.getClickCount();
        this.title=entity.getTitle();
        this.fileName=entity.getFileName();
        this.modifiedDate=entity.getModifiedDate();
    }
}
