package com.pjh.share.api;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.service.ChatRoomService;
import com.pjh.share.web.dto.ChatRoomCreateDto;
import com.pjh.share.web.dto.ChatRoomCreateResponseDto;
import com.pjh.share.web.dto.ChatRoomInviteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChatRoomApiController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/api/v1/chatroom")
    public ChatRoomCreateResponseDto create(@RequestBody ChatRoomCreateDto request,@CurrentUser SessionUser user){
        return chatRoomService.create(request,user);
    }

    @PostMapping("/api/v1/invite")
    public Result inviteToChatRoom(@RequestBody ChatRoomInviteDto request, @CurrentUser SessionUser user){
        return new Result(chatRoomService.inviteToChatRoom(request,user));
    }

    @Data
    @AllArgsConstructor
    class Result<T>{
        private T data;
    }
}
