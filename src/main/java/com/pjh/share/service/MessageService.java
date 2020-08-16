package com.pjh.share.service;

import com.pjh.share.domain.chatroom.ChatRoom;
import com.pjh.share.domain.chatroom.ChatRoomRepository;
import com.pjh.share.domain.message.Message;
import com.pjh.share.domain.message.MessageRepository;
import com.pjh.share.web.dto.ChatMessageDto;
import com.pjh.share.web.dto.ChatMessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageService {
    private ChatRoomRepository chatRoomRepository;
    private MessageRepository messageRepository;
    private final static Integer PAGE_SIZE=20;
    @Transactional
    public Long save(ChatMessageDto chatMessageDto){
        ChatRoom chatRoom=chatRoomRepository.findByRoomKey(chatMessageDto.getRoomKey());
        Message message=Message.builder()
                .chatRoom(chatRoom)
                .name(chatMessageDto.getSender())
                .content(chatMessageDto.getContent())
                .build();
        return messageRepository.save(message).getId();
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> findAllDesc(int curPage){
        Pageable pageable= PageRequest.of(curPage-1,PAGE_SIZE,new Sort(Sort.Direction.DESC,"id"));
        return messageRepository.findAll(pageable)
                .stream()
                .map(ChatMessageResponseDto::new)
                .collect(Collectors.toList());
    }
}
