package com.pjh.share.service;

import com.pjh.share.domain.comment.Comment;
import com.pjh.share.domain.comment.CommentRepository;
import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.web.dto.CommentListResponseDto;
import com.pjh.share.web.dto.CommentSaveRequestDto;
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
    public Long save(CommentSaveRequestDto requestDto)throws Exception{
        System.out.println("CommentService : "+requestDto.getContent());
        Posts post=postsRepository.findById(requestDto.getPostId())
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다"));
        Comment comment=commentRepository.save(requestDto.toEntity());
        comment.setPosts(post);
        return comment.getId();
    }

    @Transactional
    public List<CommentListResponseDto> findAllDesc(Integer curPage, Long postId){
        Pageable pageable= PageRequest.of(curPage,pageSize,new Sort(Sort.Direction.DESC,"id"));
        return commentRepository.findAllDesc(pageable,postId)
                .stream()
                .map(CommentListResponseDto::new)
                .collect(Collectors.toList());
    }
}
