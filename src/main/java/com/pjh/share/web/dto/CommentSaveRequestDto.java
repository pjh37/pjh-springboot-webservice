package com.pjh.share.web.dto;

import com.pjh.share.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentSaveRequestDto {
    private Long postId;
    private Long parentId;
    private String name;
    private String content;
    private Integer likeCount;
    private Integer dislikeCount;
    public Comment toEntity(){
        return Comment.builder()
                .name(name)
                .content(content)
                .likeCount(0)
                .dislikeCount(0)
                .childCount(0)
                .build();
    }
}
