package com.pjh.share.service;


import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.web.dto.PostsResponseDto;
import com.pjh.share.web.dto.PostsSaveRequestDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class PostServiceTest {

    private PostService postService;

    private PostUnitRepository postUnitRepository;

    @BeforeEach
    public void setUp(){
        Posts post= Posts.builder()
                .title("제목")
                .content("내용")
                .clickCount(0L)
                .name("홍길동")
                .groupId(1L)
                .build();
        postUnitRepository=new PostUnitRepository();
        postUnitRepository.save(post);
        postService=new PostService(postUnitRepository);
    }


    @Test
    public void 게시글_조회(){
        Posts post=postService.findByName("홍길동");
        assertThat(post.getName()).isEqualTo("홍길동");
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");
    }
}
