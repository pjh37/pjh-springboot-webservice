package com.pjh.share.domain.post;

import com.pjh.share.domain.BaseTimeEntity;
import com.pjh.share.domain.comment.Comment;
import com.pjh.share.domain.group.Group;
import com.pjh.share.web.dto.CommentSaveRequestDto;
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
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="GROUPS_ID")
    private Group group;

    @OneToMany(mappedBy = "posts",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments=new ArrayList<>();

    @Column(length = 500,nullable = false)
    private String name;

    @Column(length = 500,nullable = false)
    private String title;

    @Column(length = 5000,columnDefinition = "TEXT",nullable = false)
    private String content;

    private Long clickCount;


    @Builder
    public Posts(Long groupId, String name, String title, String content,Long clickCount){
        this.name=name;
        this.title=title;
        this.content=content;
        this.clickCount=clickCount;
    }
    public void update(String title,String content){
        this.title=title;
        this.content=content;
    }
    public void setGroup(Group group){
        this.group=group;
        group.getPosts().add(this);
    }

    public void postClicked(){
        clickCount++;
    }
}
