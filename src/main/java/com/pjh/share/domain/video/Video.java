package com.pjh.share.domain.video;

import com.pjh.share.domain.BaseTimeEntity;
import com.pjh.share.domain.group.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Video extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="GROUPS_ID")
    private Group group;

    private String name;

    private String title;

    @Column(length = 500,columnDefinition = "TEXT",nullable = false)
    private String fileName;

    @Column(length = 500,columnDefinition = "TEXT",nullable = false)
    private String fileOriginalName;

    private Long clickCount=0L;

    @Builder
    public Video(String title,String fileName,String fileOriginalName){
        this.title=title;
        this.fileName=fileName;
        this.fileOriginalName=fileOriginalName;
    }


    public void clickCountUpdate(){
        clickCount++;
    }
}
