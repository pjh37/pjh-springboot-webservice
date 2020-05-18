package com.pjh.share.domain.file;

import com.pjh.share.domain.BaseTimeEntity;
import com.pjh.share.domain.group.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class File extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="groups_id")
    private Long groups_id;

    @JoinColumn(name="posts_id")
    private Long posts_id;

    @Column(length = 500,columnDefinition = "TEXT",nullable = false)
    private String fileName;

    @Column(length = 500,columnDefinition = "TEXT",nullable = false)
    private String fileOriginalName;

    @Builder
    public File(String fileName,String fileOriginalName){
        this.fileName=fileName;
        this.fileOriginalName=fileOriginalName;
    }
    public void groupIdUpdate(Long groupId){
        this.groups_id=groupId;
    }
    public void postIdUpdate(Long postId){
        this.posts_id=postId;
    }
}
