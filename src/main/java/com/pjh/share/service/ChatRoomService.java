package com.pjh.share.service;

import com.pjh.share.domain.chatroom.ChatRoom;
import com.pjh.share.domain.chatroom.ChatRoomRepository;
import com.pjh.share.web.dto.ChatRoomCreateDto;
import com.pjh.share.web.dto.ChatRoomCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatRoomCreateResponseDto create(ChatRoomCreateDto request){
        ChatRoom chatRoom=chatRoomRepository.save(request.toEntity());
        return new ChatRoomCreateResponseDto(chatRoom.getRoomKey());
    }


}
