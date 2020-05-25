package com.pjh.share.web;

import com.pjh.share.service.CommentService;
import com.pjh.share.web.dto.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final CommentService commentService;
    @PostMapping("/api/comment")
    public Long save(@RequestBody CommentSaveRequestDto requestDto) throws Exception{
        System.out.println("CommentApiController : "+requestDto.getContent());
        return commentService.save(requestDto);
    }
}
