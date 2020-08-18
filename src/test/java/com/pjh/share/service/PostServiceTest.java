package com.pjh.share.service;


import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.web.dto.PostsResponseDto;
import com.pjh.share.web.dto.PostsSaveRequestDto;

import com.pjh.share.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class PostServiceTest {

    private PostService postService;

    private PostUnitRepository postUnitRepository;

    @BeforeEach
    public void setUp(){
        postUnitRepository=new PostUnitRepository();
        postService=new PostService(postUnitRepository);
    }


    @Test
    public void 게시글_조회(){
        Posts post= Posts.builder()
                .title("제목")
                .content("내용")
                .clickCount(0L)
                .name("홍길동")
                .groupId(1L)
                .build();
        Long savedId=postUnitRepository.save(post).getId();
        Posts savedPost=postService.findById(savedId);
        assertThat(savedPost.getName()).isEqualTo("홍길동");
        assertThat(savedPost.getTitle()).isEqualTo("제목");
        assertThat(savedPost.getContent()).isEqualTo("내용");
    }

    @Test
    public void 게시글_수정(){
        Posts post= Posts.builder()
                .title("제목")
                .content("내용")
                .clickCount(0L)
                .name("홍길동")
                .groupId(1L)
                .build();
        PostsUpdateRequestDto requestDto=new PostsUpdateRequestDto();
        requestDto.setTitle("변경된제목");
        requestDto.setContent("변경된내용");

        Long savedId=postUnitRepository.save(post).getId();
        postService.update(savedId,requestDto);

        Posts updatedPost=postService.findById(savedId);
        assertThat(updatedPost.getName()).isEqualTo("홍길동");
        assertThat(updatedPost.getTitle()).isEqualTo("변경된제목");
        assertThat(updatedPost.getContent()).isEqualTo("변경된내용");
    }

    @Test
    public void 게시글_삭제(){
        Posts post= Posts.builder()
                .title("제목")
                .content("내용")
                .clickCount(0L)
                .name("홍길동")
                .groupId(1L)
                .build();
        Long savedId=postUnitRepository.save(post).getId();

        postService.delete(savedId);

        Posts deletedPost=postService.findById(savedId);
        assertThat(deletedPost).isNotEqualTo(post);
    }
}
