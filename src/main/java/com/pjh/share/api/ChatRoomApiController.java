package com.pjh.share.api;

import com.pjh.share.service.ChatRoomService;
import com.pjh.share.web.dto.ChatRoomCreateDto;
import com.pjh.share.web.dto.ChatRoomCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomApiController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/api/chatroom")
    public ChatRoomCreateResponseDto create(@RequestBody ChatRoomCreateDto request){
        return chatRoomService.create(request);
    }

}
