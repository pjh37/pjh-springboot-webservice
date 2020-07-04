package com.pjh.share.api;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.service.CommentService;
import com.pjh.share.web.dto.CommentDeleteRequestDto;
import com.pjh.share.web.dto.CommentListResponseDto;
import com.pjh.share.web.dto.CommentSaveRequestDto;
import com.pjh.share.web.dto.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final Integer firstPage=0;
    private final CommentService commentService;

    @GetMapping("/api/comment/{id}")
    public List<CommentListResponseDto> get(@PathVariable Long id) {
        return commentService.findAllChildByIdDesc(firstPage,id);
    }

    @PostMapping("/api/comment")
    public Long save(@RequestBody CommentSaveRequestDto requestDto, @CurrentUser Account account) throws Exception{
        if(!requestDto.getName().equals(account.getName()))return -1L;
        return commentService.save(requestDto);
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
