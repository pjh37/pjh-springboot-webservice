package com.pjh.share.domain.group;

import com.pjh.share.domain.BaseTimeEntity;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.groupaccount.GroupAccount;
import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.video.Video;
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
@Table(name="groups")
public class Group extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUPS_ID")
    private Long id;

    //그룹을 만든 사람
    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID")
    private Account account;

    private String thumbnail;

    @OneToMany(mappedBy = "group",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Posts> posts=new ArrayList<>();

    @OneToMany(mappedBy = "group",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Video> videos=new ArrayList<>();

    @OneToMany(mappedBy = "group",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupAccount> groupAccounts=new ArrayList<>();

    @Column(length = 500,nullable = false)
    private String title;

    @Column(length = 2000,nullable = false)
    private String description;

    @Column(columnDefinition = "integer default 0")
    private Integer currentNum;

    @Column(nullable = false,columnDefinition = "integer default 0")
    private Integer totalNum;

    @Column(length = 500)
    private String password;



    @Builder
    public Group(String title,String description,String password,String thumbnail,Integer totalNum,Integer currentNum){
        this.title=title;
        this.description=description;
        this.password=password;
        this.thumbnail=thumbnail;
        this.currentNum=currentNum;
        this.totalNum=totalNum;
    }
    public void thumbnailUpdate(String thumbnail){
        this.thumbnail=thumbnail;
    }
    public void currentNumInc(){
        this.currentNum+=1;
    }
    public void currentNumDesc(){
        if(this.currentNum<=0)return;
        this.currentNum-=1;
    }

}
