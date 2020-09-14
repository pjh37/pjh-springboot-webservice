package com.pjh.share.web.dto;

import com.pjh.share.domain.video.Video;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoListResponseDto {
    private Long id;
    private Long clickCount;
    private String name;
    private String title;
    private String url;
    private LocalDateTime modifiedDate;

    public VideoListResponseDto(Video entity){
        this.id=entity.getId();
        this.name=entity.getName();
        this.clickCount=entity.getClickCount();
        this.title=entity.getTitle();
        this.url=entity.getUrl();
        this.modifiedDate=entity.getModifiedDate();
    }
}
