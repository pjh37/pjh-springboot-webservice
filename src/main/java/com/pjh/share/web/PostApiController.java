package com.pjh.share.web;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.service.PostService;
import com.pjh.share.web.dto.PostsResponseDto;
import com.pjh.share.web.dto.PostsSaveRequestDto;
import com.pjh.share.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @GetMapping("/api/post/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postService.findById(id);
    }

    @PostMapping("/api/post")
    public Long save(@RequestBody PostsSaveRequestDto requestDto, @CurrentUser Account account){
        System.out.println("name : "+requestDto.getName());
        return postService.save(requestDto,account);
    }

    @PutMapping("/api/post/{id}")
    public Long update(@PathVariable Long id,@RequestBody PostsUpdateRequestDto requestDto){
        return postService.update(id,requestDto);
    }

    @DeleteMapping("/api/post/{id}")
    public Long delete(@PathVariable Long id){
        postService.delete(id);
        return id;
    }
}
