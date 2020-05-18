package com.pjh.share.domain.comment;

import com.pjh.share.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500,nullable = false)
    private String name;

    @Column(length = 2000,nullable = false)
    private String content;

    @Column(nullable = false,columnDefinition = "integer default 0")
    private Integer likeCount;

    @Column(nullable = false,columnDefinition = "integer default 0")
    private Integer dislikeCount;

    @Builder
    public Comment(String name,String content){
        this.name=name;
        this.content=content;
    }
}
