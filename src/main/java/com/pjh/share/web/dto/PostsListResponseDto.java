package com.pjh.share.web.dto;

import com.pjh.share.domain.post.Posts;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class PostsListResponseDto {
    private Long id;
    private String name;
    private String title;
    private String content;
    private String modifiedDate;
    private Long clickCount;

    public PostsListResponseDto(Posts entity){
        this.id=entity.getId();
        this.name=entity.getName();
        this.title=entity.getTitle();
        this.content=entity.getContent();
        this.modifiedDate=entity.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.clickCount=entity.getClickCount();
    }
}
