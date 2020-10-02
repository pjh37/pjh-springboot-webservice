package com.pjh.share.service;

import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.comment.Comment;
import com.pjh.share.domain.comment.CommentRepository;
import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.web.dto.CommentDeleteRequestDto;
import com.pjh.share.web.dto.CommentListResponseDto;
import com.pjh.share.web.dto.CommentSaveRequestDto;
import com.pjh.share.web.dto.CommentUpdateRequestDto;
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
import static org.mockito.Mockito.*;

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
        comment.setId(1L);
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
    void findAllDesc() throws Exception{
        //given
       List<CommentListResponseDto> comments=new ArrayList<>();
        Pageable pageable= PageRequest.of(curPage,pageSize,Sort.by("id").descending());
        given(commentRepository.findAllDesc(pageable,POST_ID)
                .stream()
                .map(CommentListResponseDto::new)
                .collect(Collectors.toList())).willReturn(comments);
        //when
        commentService.findAllDesc(curPage,POST_ID);

        verify(commentRepository,times(1)).findAllDesc(pageable,POST_ID);

    }

    @Test
    void findAllChildByIdDesc() {
    }

    @Test
    void update() {
        //given
        CommentUpdateRequestDto commentUpdateRequestDto=buildCommentUpdateRequestDto();
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));

        //when
        commentService.update(commentUpdateRequestDto);

        //then
        verify(commentRepository,times(1)).findById(comment.getId());
        assertThat(commentService.findById(comment.getId()).getContent()).isEqualTo(commentUpdateRequestDto.getContent());
    }

    @Test
    void delete() {
        //given
        CommentDeleteRequestDto commentDeleteRequestDto=new CommentDeleteRequestDto();
        commentDeleteRequestDto.setCommentId(1L);
        given(commentRepository.findById(1L)).willReturn(Optional.of(comment));
        doNothing().when(commentRepository).delete(comment);

        //when
        commentService.delete(commentDeleteRequestDto);
        verify(commentRepository,times(1)).findById(1L);
        verify(commentRepository,times(1)).delete(comment);


        //then
        assertThat(commentService.findAllDesc(curPage,POST_ID).size()).isEqualTo(0);

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

    CommentUpdateRequestDto buildCommentUpdateRequestDto(){
        CommentUpdateRequestDto commentUpdateRequestDto=new CommentUpdateRequestDto();
        commentUpdateRequestDto.setCommentId(comment.getId());
        commentUpdateRequestDto.setContent("수정내용용");
        return commentUpdateRequestDto;
    }

    Comment buildCommentListResponseDto(){
        return Comment.builder()
                .name("작성자")
                .content("댓글내용")
                .likeCount(0)
                .dislikeCount(0)
                .childCount(0)
                .build();
    }
}