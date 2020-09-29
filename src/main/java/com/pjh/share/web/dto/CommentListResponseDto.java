package com.pjh.share.web.dto;


import com.pjh.share.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentListResponseDto {
    private Long id;
    private String name;
    private String content;
    private Integer likeCount;
    private Integer dislikeCount;
    private List<CommentListResponseDto> children;
    private Integer childCount;
    private String modifiedDate;

    public CommentListResponseDto(Comment entity){
        this.id=entity.getId();
        this.name=entity.getName();
        this.content=entity.getContent();
        this.likeCount=entity.getLikeCount();
        this.dislikeCount=entity.getDislikeCount();
        this.children=entity.getChild().stream()
                .map(CommentListResponseDto::new)
                .collect(Collectors.toList());
        this.childCount=entity.getChild().size();
        this.modifiedDate=entity.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


}
