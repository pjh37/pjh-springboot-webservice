package com.pjh.share.service;

import com.pjh.share.domain.group.Group;
import com.pjh.share.domain.group.GroupRepository;
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

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostsRepository postRepository;
    private final GroupRepository groupRepository;
    private final Integer pageSize=10;
    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        Group group=groupRepository.findById(requestDto.getGroupId()).orElseThrow(()->new IllegalArgumentException("해당 그룹이 없습니다"));;
        Posts post=postRepository.save(requestDto.toEntity());
        post.setGroup(group);
        return post.getId();
    }

    @Transactional
    public List<PostsListResponseDto> findAllDesc(Integer curPage){
        Pageable pageable= PageRequest.of(curPage,pageSize,new Sort(Sort.Direction.DESC,"id"));
        return postRepository.findAllDesc(pageable)
                .stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id){
        Posts post=postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다"));
        return new PostsResponseDto(post);
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts post=postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다"));
        post.update(requestDto.getTitle(),requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete(Long id){
        Posts post=postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다"));
        postRepository.delete(post);
    }
}
