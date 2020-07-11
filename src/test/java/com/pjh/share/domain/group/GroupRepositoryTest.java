package com.pjh.share.domain.group;

import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GroupRepositoryTest {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PostsRepository postsRepository;

    @After
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

        assertEquals(savedGroup.getTitle(),title);
        assertEquals(savedGroup.getDescription(),des);
        assertEquals(savedGroup.getTotalNum(),totalNum);
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

        assertEquals(updatedGroup.getTitle(),updatedTitle);
        assertEquals(updatedGroup.getDescription(),updatedDes);
        assertEquals(updatedGroup.getTotalNum(),updatedTotalNum);

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
        assertEquals(beforeCount,1);
        groupRepository.delete(savedGroup);
        int afterCount=groupRepository.findAll().size();
        assertEquals(afterCount,0);
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
        assertEquals(savedPost.getName(),name);
        assertEquals(savedPost.getTitle(),postTitle);
        assertEquals(savedPost.getContent(),postContent);
    }

    @Test
    public void 그룹별_게시글불러오기(){

    }

    @Test
    public void 그룹생성_게시글들작성_게시글들불러오기(){

    }

    @Test
    public void 그룹생성_게시글들작성_그룹삭제(){

    }

    @Test
    public void 그룹생성_게시글작성_댓글작성(){

    }

    @Test
    public void 그룹생성_게시글작성_댓글작성_게시글삭제(){

    }

    @Test
    public void 그룹생성_게시글작성_댓글작성_댓글수정(){

    }

    @Test
    public void 그룹생성_게시글작성_댓글작성_댓글삭제(){

    }



}
