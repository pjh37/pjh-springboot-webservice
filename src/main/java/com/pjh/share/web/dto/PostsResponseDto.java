package com.pjh.share.web.dto;

import com.pjh.share.domain.post.Posts;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostsResponseDto {
    private Long id;
    private String name;
    private String title;
    private String content;

    public PostsResponseDto(Posts entity){
        this.id=entity.getId();
        this.name=entity.getName();
        this.title=entity.getTitle();
        this.content=entity.getContent();
    }
}
