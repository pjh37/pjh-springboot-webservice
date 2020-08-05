package com.pjh.share.service;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.chatroom.ChatRoom;
import com.pjh.share.domain.chatroom.ChatRoomRepository;
import com.pjh.share.domain.chatroomaccount.ChatRoomAccount;
import com.pjh.share.domain.chatroomaccount.ChatRoomAccountRepository;
import com.pjh.share.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomAccountRepository chatRoomAccountRepository;

    @Transactional
    public ChatRoomCreateResponseDto create(ChatRoomCreateDto request, Account account){
        ChatRoom chatRoom=chatRoomRepository.save(request.toEntity());
        ChatRoomAccount chatRoomAccount=ChatRoomAccount.builder()
                .account(account)
                .chatRoom(chatRoom)
                .build();
        chatRoomAccountRepository.save(chatRoomAccount);
        return new ChatRoomCreateResponseDto(chatRoom.getRoomKey());
    }

    @Transactional(readOnly = true)
    public List<ChatRoomListResponseDto> findAllMyChatRoom(Long id){
        return chatRoomAccountRepository.findAllMyChatRoom(id)
                .stream()
                .map(ChatRoomListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ChatRoomInviteResponseDto inviteToChatRoom(ChatRoomInviteDto request, Account account){
        ChatRoom chatRoom=chatRoomRepository.findByRoomKey(request.getRoomKey());
        ChatRoomAccount chatRoomAccount=ChatRoomAccount.builder()
                .account(account)
                .chatRoom(chatRoom)
                .build();
        chatRoomAccountRepository.save(chatRoomAccount);
        return new ChatRoomInviteResponseDto(true);
    }
}
