package com.pjh.share.service;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostsRepository postRepository;
    private final GroupRepository groupRepository;
    private final static Integer PAGE_SIZE=10;
    private final static Integer BLOCK_PAGE_SIZE=4;
    @Transactional
    public Long save(PostsSaveRequestDto requestDto,Account account){
        Group group=groupRepository.findById(requestDto.getGroupId()).orElseThrow(()->new IllegalArgumentException("해당 그룹이 없습니다"));;
        requestDto.setName(account.getName());
        Posts post=postRepository.save(requestDto.toEntity());
        post.setGroup(group);
        return post.getId();
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(Integer curPage,Long groupId){
        Pageable pageable= PageRequest.of(curPage-1,PAGE_SIZE,new Sort(Sort.Direction.DESC,"id"));
        return postRepository.findAllDesc(pageable,groupId)
                .stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long getPostCount(Long groupId){
        return postRepository.findGroupPostCount(groupId);
    }

    public List<Integer> getPageList(Integer curPage,Long groupId){
        //Integer[] pageList=new Integer[BLOCK_PAGE_SIZE];
        List<Integer> pageList=new ArrayList<>();
        Double postTotalCount=Double.valueOf(getPostCount(groupId));

        //총 게시글 기준 올림
        Integer totalLastPageNum=(int)(Math.ceil((postTotalCount/PAGE_SIZE)));

        Integer blockLastPageNum=(totalLastPageNum>curPage+BLOCK_PAGE_SIZE)
                ? curPage+BLOCK_PAGE_SIZE:totalLastPageNum;

        curPage=(curPage<=3) ? 1: curPage-2;

        for(int val=curPage,idx=0;val<=blockLastPageNum;val++,idx++){
            pageList.add(val);
        }
        return pageList;
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
