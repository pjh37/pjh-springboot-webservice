package com.pjh.share.web.dto;


import com.pjh.share.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class CommentListResponseDto {
    private Long id;
    private String name;
    private String content;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer childCount;
    private String modifiedDate;

    public CommentListResponseDto(Comment entity){
        this.id=entity.getId();
        this.name=entity.getName();
        this.content=entity.getContent();
        this.likeCount=entity.getLikeCount();
        this.dislikeCount=entity.getDislikeCount();
        this.childCount=entity.getChildCount();
        this.modifiedDate=entity.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


}
