package com.pjh.share.domain.posts;

import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanUp(){
        postsRepository.deleteAll();
    }

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
        assertEquals(post.getName(),name);
        assertEquals(post.getContent(),content);
        assertEquals(post.getTitle(),title);
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
        assertEquals(post.getName(),name);
        assertEquals(post.getContent(),content2);
        assertEquals(post.getTitle(),title2);
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
        assertEquals(1,postsRepository.findAll().size());
        postsRepository.delete(post);
        assertEquals(0,postsRepository.findAll().size());
    }
}
