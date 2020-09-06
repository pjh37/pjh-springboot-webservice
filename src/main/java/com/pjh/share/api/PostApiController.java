package com.pjh.share.api;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.service.PostService;
import com.pjh.share.web.dto.PostsListResponseDto;
import com.pjh.share.web.dto.PostsResponseDto;
import com.pjh.share.web.dto.PostsSaveRequestDto;
import com.pjh.share.web.dto.PostsUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @GetMapping("/api/post/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postService.findById(id);
    }

    @PostMapping("/api/post")
    public Long save(@RequestBody PostsSaveRequestDto requestDto, @CurrentUser SessionUser user){
        return postService.save(requestDto,user);
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

    @GetMapping("/api/v1/group/{id}")
    public Result<List<PostsListResponseDto>> findPostByPage(@PathVariable("id") Long id,
                                                     @RequestParam(value = "page",defaultValue = "1")Integer pageNum,
                                                     @CurrentUser SessionUser user){
        return new Result<List<PostsListResponseDto>>(postService.findAllDesc(pageNum,id),postService.getPageList(pageNum,id),id);
    }

    @Data
    @AllArgsConstructor
    class Result<T>{
        private T data;
        private List<Integer> pageList;
        private Long groupId;
    }
}
