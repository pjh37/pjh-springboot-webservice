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

    private Long parent;//부모 댓글의 id

    @Column(length = 500,nullable = false)
    private String name;

    @Column(length = 2000,nullable = false)
    private String content;

    @Column(nullable = false,columnDefinition = "integer default 0")
    private Integer likeCount;

    @Column(nullable = false,columnDefinition = "integer default 0")
    private Integer dislikeCount;

    private Integer childCount;

    @Builder
    public Comment(Long parentId,String name,String content,Integer likeCount,Integer dislikeCount,Integer childCount){
        this.parent=parentId;
        this.name=name;
        this.content=content;
        this.likeCount=likeCount;
        this.dislikeCount=dislikeCount;
        this.childCount=childCount;
    }
    public void update(String content){
        this.content=content;
    }
}
