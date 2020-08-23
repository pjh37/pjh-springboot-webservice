package com.pjh.share.repository;

import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class PostRepositoryTest {
    @Autowired
    private PostsRepository postsRepository;

    @BeforeEach
    public void cleanUp(){ }

    @Test
    public void 게시글저장하기_불러오기(){
        String name="홍길동";
        String title="게시글제목";
        String content="게시글내용입니다";

        postsRepository.save(Posts.builder()
                .content(content)
                .name(name)
                .title(title)
                .groupId(0L)
                .build());
        List<Posts> postsList=postsRepository.findAll();
        Posts post=postsList.get(0);
        assertThat(post.getName()).isEqualTo(name);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getTitle()).isEqualTo(title);
    }

    @Test
    public void 게시글저장하기_수정하기(){
        String name="홍길동";
        String title="게시글제목";
        String content="게시글내용입니다";

        postsRepository.save(Posts.builder()
                .content(content)
                .name(name)
                .title(title)
                .groupId(0L)
                .build());
        List<Posts> postsList=postsRepository.findAll();
        Posts post=postsList.get(0);
        String title2="수정된게시글제목";
        String content2="수정된게시글내용입니다";
        post.update(title2,content2);

        assertThat(post.getName()).isEqualTo(name);
        assertThat(post.getContent()).isEqualTo(content2);
        assertThat(post.getTitle()).isEqualTo(title2);
    }

    @Test
    public void 게시글저장하기_삭제하기(){
        String name="홍길동";
        String title="게시글제목";
        String content="게시글내용입니다";

        Posts post=postsRepository.save(Posts.builder()
                .content(content)
                .name(name)
                .title(title)
                .groupId(0L)
                .build());
        assertThat(postsRepository.findAll().size()).isEqualTo(1);
        postsRepository.delete(post);
        assertThat(postsRepository.findAll().size()).isEqualTo(0);
    }
}
