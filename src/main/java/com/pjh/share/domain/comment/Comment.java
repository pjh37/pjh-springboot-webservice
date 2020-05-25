package com.pjh.share.domain.comment;

import com.pjh.share.domain.BaseTimeEntity;
import com.pjh.share.domain.post.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="POSTS_ID")
    private Posts posts;

    private Long parent=-1L;//부모 댓글의 id

    @Column(length = 500,nullable = false)
    private String name;

    @Column(length = 2000,nullable = false)
    private String content;

    @Column(nullable = false,columnDefinition = "integer default 0")
    private Integer likeCount;

    @Column(nullable = false,columnDefinition = "integer default 0")
    private Integer dislikeCount;

    @Builder
    public Comment(Long postId,String name,String content,Integer likeCount,Integer dislikeCount){
        this.name=name;
        this.content=content;
        this.likeCount=likeCount;
        this.dislikeCount=dislikeCount;
    }

}
