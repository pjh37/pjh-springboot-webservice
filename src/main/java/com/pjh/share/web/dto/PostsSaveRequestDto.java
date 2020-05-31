package com.pjh.share.web.dto;

import com.pjh.share.domain.post.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private Long groupId;
    private String title;
    private String name;
    private String content;

    public Posts toEntity(){
        return Posts.builder()
                .groupId(groupId)
                .title(title)
                .content(content)
                .build();
    }
}
