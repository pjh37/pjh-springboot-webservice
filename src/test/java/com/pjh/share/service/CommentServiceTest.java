package com.pjh.share.service;

import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.comment.Comment;
import com.pjh.share.domain.comment.CommentRepository;
import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.web.dto.CommentListResponseDto;
import com.pjh.share.web.dto.CommentSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    private static final Long GROUP_ID=1L;
    private static final Long POST_ID=1L;
    private static final Long USER_ID=1L;
    private static final Integer page=1;
    private static final Integer curPage=0;
    private static final Integer pageSize=10;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostsRepository postRepository;

    private CommentService commentService;

    private SessionUser sessionUser;

    private Comment comment;

    private Posts post;
    @BeforeEach
    public void setup(){
        commentService=new CommentService(commentRepository,postRepository);
        post=Posts.builder()
                .content("게시글내용")
                .name("작성자")
                .title("제목")
                .groupId(GROUP_ID)
                .build();

        comment=Comment.builder()
                .name("작성자")
                .content("댓글내용")
                .likeCount(0)
                .dislikeCount(0)
                .childCount(0)
                .build();
        comment.setPosts(post);
        comment.setModifiedDate(LocalDateTime.now());
        sessionUser= SessionUser.builder()
                .name("user")
                .id(USER_ID)
                .build();
    }
    @Test
    @DisplayName("댓글 저장")
    void save() throws Exception{
        //given
        CommentSaveRequestDto commentSaveRequestDto=buildCommentSaveRequestDto();

        given(commentRepository.save(any())).willReturn(comment);
        given(postRepository.findById(any())).willReturn(Optional.of(post));
        given(commentRepository.findById(POST_ID)).willReturn(Optional.of(comment));

        //when
        commentService.save(commentSaveRequestDto);
        CommentListResponseDto commentListResponseDto=commentService.findById(POST_ID);

        //then
        assertThat(commentListResponseDto.getContent()).isEqualTo(comment.getContent());
        assertThat(commentListResponseDto.getName()).isEqualTo(comment.getName());
    }

    @Test
    void findAllDesc() {
    }

    @Test
    void findAllChildByIdDesc() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    CommentSaveRequestDto buildCommentSaveRequestDto(){
        CommentSaveRequestDto commentSaveRequestDto=new CommentSaveRequestDto();
        commentSaveRequestDto.setName("작성자");
        commentSaveRequestDto.setContent("댓글내용");
        commentSaveRequestDto.setDislikeCount(0);
        commentSaveRequestDto.setLikeCount(0);
        commentSaveRequestDto.setParentId(-1L);
        commentSaveRequestDto.setPostId(1L);
        return commentSaveRequestDto;
    }
}