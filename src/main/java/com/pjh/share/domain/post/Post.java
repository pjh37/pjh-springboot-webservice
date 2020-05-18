package com.pjh.share.domain.post;

import com.pjh.share.domain.BaseTimeEntity;
import com.pjh.share.domain.group.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="groups_id")
    private Group group;

    @Column(length = 500,nullable = false)
    private String name;

    @Column(length = 500,nullable = false)
    private String title;

    @Column(length = 5000,columnDefinition = "TEXT",nullable = false)
    private String content;

    @Builder
    public Post(String name,String title,String content){
        this.name=name;
        this.title=title;
        this.content=content;
    }

}
