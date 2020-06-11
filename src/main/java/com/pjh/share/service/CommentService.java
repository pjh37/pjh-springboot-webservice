package com.pjh.share.service;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.comment.Comment;
import com.pjh.share.domain.comment.CommentRepository;
import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostsRepository postsRepository;
    private final Integer pageSize=10;
    @Transactional
    public Long save(CommentSaveRequestDto requestDto,Account account)throws Exception{
        System.out.println("CommentService : "+requestDto.getContent());
        Posts post=postsRepository.findById(requestDto.getPostId())
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다"));
        requestDto.setName(account.getName());
        Comment comment=commentRepository.save(requestDto.toEntity());
        comment.setPosts(post);
        if(comment.getParent()!=-1){
            Comment parentComment=commentRepository.findById(comment.getParent())
                    .orElseThrow(()->new IllegalArgumentException("부모댓글 없음"));
            parentComment.setChildCount(parentComment.getChildCount()+1);
        }
        return comment.getId();
    }

    @Transactional(readOnly = true)
    public List<CommentListResponseDto> findAllDesc(Integer curPage, Long postId){
        Pageable pageable= PageRequest.of(curPage,pageSize,new Sort(Sort.Direction.DESC,"id"));
        return commentRepository.findAllDesc(pageable,postId)
                .stream()
                .map(CommentListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<CommentListResponseDto> findAllChildByIdDesc(Integer curPage, Long parentId){
        Pageable pageable= PageRequest.of(curPage,pageSize,new Sort(Sort.Direction.DESC,"id"));
        return commentRepository.findAllChildByIdDesc(pageable,parentId)
                .stream()
                .map(CommentListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public Long update(CommentUpdateRequestDto requestDto){
        Comment comment=commentRepository.findById(requestDto.getCommentId())
                .orElseThrow(()->new IllegalArgumentException("없는 댓글입니다."));
        comment.update(requestDto.getContent());
        return comment.getId();
    }

    @Transactional
    public Long delete(CommentDeleteRequestDto requestDto){
        Comment comment=commentRepository.findById(requestDto.getCommentId())
                .orElseThrow(()->new IllegalArgumentException("없는 댓글입니다."));
        commentRepository.delete(comment);
        return comment.getId();
    }
}
