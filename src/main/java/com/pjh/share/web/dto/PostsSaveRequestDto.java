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
    private String name;
    private String title;
    private String content;

    public Posts toEntity(){
        return Posts.builder()
                .groupId(groupId)
                .name(name)
                .title(title)
                .content(content)
                .build();
    }
}
