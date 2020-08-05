package com.pjh.share.api;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
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
    public ChatRoomCreateResponseDto create(@RequestBody ChatRoomCreateDto request,@CurrentUser Account account){
        return chatRoomService.create(request,account);
    }

    @PostMapping("/api/v1/invite")
    public Result inviteToChatRoom(@RequestBody ChatRoomInviteDto request, @CurrentUser Account account){
        return new Result(chatRoomService.inviteToChatRoom(request,account));
    }

    @Data
    @AllArgsConstructor
    class Result<T>{
        private T data;
    }
}
