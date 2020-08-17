package com.pjh.share.repository;

import com.pjh.share.domain.group.Group;
import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Transactional
public class GroupRepositoryTest {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PostsRepository postsRepository;

    @BeforeEach
    public void cleanUp(){
        groupRepository.deleteAll();
        postsRepository.deleteAll();
    }

    @Test
    public void 그룹생성_불러오기(){
        String title="스프링 부트 스터디 모집";
        String des="선착순모집입니다.";
        Integer totalNum=10;
        Group group=Group.builder()
                .title(title)
                .description(des)
                .totalNum(totalNum)
                .build();
        Group savedGroup=groupRepository.save(group);

        assertThat(savedGroup.getTitle()).isEqualTo(title);
        assertThat(savedGroup.getDescription()).isEqualTo(des);
        assertThat(savedGroup.getTotalNum()).isEqualTo(totalNum);
    }

    @Test
    public void 그룹생성_수정하기(){
        String title="스프링 부트 스터디 모집";
        String des="선착순모집입니다.";
        Integer totalNum=10;

        String updatedTitle="토익 스터디 모집";
        String updatedDes="900이상을 목표로";
        Integer updatedTotalNum=7;

        Group group=Group.builder()
                .title(title)
                .description(des)
                .totalNum(totalNum)
                .build();

        Group savedGroup=groupRepository.save(group);
        savedGroup.setTitle(updatedTitle);
        savedGroup.setDescription(updatedDes);
        savedGroup.setTotalNum(updatedTotalNum);

        Group updatedGroup=groupRepository.findById(savedGroup.getId()).orElseThrow(()->new IllegalArgumentException());


        assertThat(updatedGroup.getTitle()).isEqualTo(updatedTitle);
        assertThat(updatedGroup.getDescription()).isEqualTo(updatedDes);
        assertThat(updatedGroup.getTotalNum()).isEqualTo(updatedTotalNum);
    }

    @Test
    public void 그룹생성_삭제하기(){
        String title="스프링 부트 스터디 모집";
        String des="선착순모집입니다.";
        Integer totalNum=10;
        Group group=Group.builder()
                .title(title)
                .description(des)
                .totalNum(totalNum)
                .build();
        Group savedGroup=groupRepository.save(group);

        int beforeCount=groupRepository.findAll().size();
        assertThat(beforeCount).isEqualTo(1);
        groupRepository.delete(savedGroup);
        int afterCount=groupRepository.findAll().size();
        assertThat(afterCount).isEqualTo(0);
    }

    @Test
    public void 그룹생성_게시글작성(){
        String title="스프링 부트 스터디 모집";
        String des="선착순모집입니다.";
        Integer totalNum=10;
        Group group=Group.builder()
                .title(title)
                .description(des)
                .totalNum(totalNum)
                .build();
        Group savedGroup=groupRepository.save(group);

        String name="홍길동";
        String postTitle="게시글제목";
        String postContent="게시글내용입니다";

        Posts post=postsRepository.save(Posts.builder()
                .content(postContent)
                .name(name)
                .title(postTitle)
                .groupId(savedGroup.getId())
                .build());


        post.setGroup(savedGroup);
        Posts savedPost=savedGroup.getPosts().get(0);

        assertThat(savedPost.getName()).isEqualTo(name);
        assertThat(savedPost.getTitle()).isEqualTo(postTitle);
        assertThat(savedPost.getContent()).isEqualTo(postContent);
    }

}
