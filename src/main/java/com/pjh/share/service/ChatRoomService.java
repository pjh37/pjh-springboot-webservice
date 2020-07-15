package com.pjh.share.service;

import com.pjh.share.domain.chatroom.ChatRoomRepository;
import com.pjh.share.web.dto.ChatRoomCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public Long create(ChatRoomCreateDto request){
        return chatRoomRepository.save(request.toEntity()).getId();
    }
}
