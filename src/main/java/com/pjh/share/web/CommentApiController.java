package com.pjh.share.web;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.service.CommentService;
import com.pjh.share.web.dto.CommentDeleteRequestDto;
import com.pjh.share.web.dto.CommentSaveRequestDto;
import com.pjh.share.web.dto.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final CommentService commentService;
    @PostMapping("/api/comment")
    public Long save(@RequestBody CommentSaveRequestDto requestDto, @CurrentUser Account account) throws Exception{
        return commentService.save(requestDto,account);
    }
    @PutMapping("/api/comment")
    public Long update(@RequestBody CommentUpdateRequestDto requestDto){
        return commentService.update(requestDto);
    }
    @DeleteMapping("/api/comment")
    public Long delete(@RequestBody CommentDeleteRequestDto requestDto){
        return commentService.delete(requestDto);
    }
}
