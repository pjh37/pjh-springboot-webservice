package com.pjh.share.domain.comment;

import com.pjh.share.domain.BaseTimeEntity;
import com.pjh.share.domain.post.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    //private Long parent;//부모 댓글의 id

    //이렇게 해야된다는데 검증해보자
    //===============
    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment parent;

    @OneToMany(mappedBy = "parent",orphanRemoval = true)
    private List<Comment> child=new ArrayList<>();

    //================

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
    public Comment(String name,String content,Integer likeCount,Integer dislikeCount,Integer childCount){
        this.name=name;
        this.content=content;
        this.likeCount=likeCount;
        this.dislikeCount=dislikeCount;
        this.childCount=childCount;
    }
    public void update(String content){
        this.content=content;
    }

    public void setPosts(Posts post){
        this.posts=post;
        post.getComments().add(this);
    }

    public void setParent(Comment comment){
        this.parent=comment;
        this.parent.childCount++;
        child.add(comment);
    }
}
