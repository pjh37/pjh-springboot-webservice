package com.pjh.share.api;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.account.Role;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.chatroom.ChatRoom;
import com.pjh.share.domain.chatroom.ChatRoomRepository;
import com.pjh.share.domain.chatroomaccount.ChatRoomAccount;
import com.pjh.share.domain.chatroomaccount.ChatRoomAccountRepository;
import com.pjh.share.service.ChatRoomService;
import com.pjh.share.web.dto.ChatRoomCreateDto;
import com.pjh.share.web.dto.ChatRoomCreateResponseDto;
import com.pjh.share.web.dto.ChatRoomInviteDto;
import com.pjh.share.web.dto.ChatRoomInviteResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class ChatRoomApiControllerTest {
    private static final Long USER_ID=1L;

    private ChatRoomApiController chatRoomApiController;

    private SessionUser user;

    @Mock
    private ChatRoomService chatRoomService;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ChatRoomAccountRepository chatRoomAccountRepository;

    @BeforeEach
    public void setup(){
        chatRoomApiController=new ChatRoomApiController(chatRoomService);
        user=SessionUser.builder()
                .name("user")
                .role(Role.USER)
                .id(USER_ID)
                .build();
    }

    @Test
    @DisplayName("채팅방 개설")
    void create() {
        //given
        Account account=toAccountEntity();
        ChatRoomCreateDto chatRoomCreateDto=buildChatRoomCreateDto();
        String roomKey= UUID.randomUUID().toString().replaceAll("-","");
        ChatRoom chatRoom=toChatRoomEntity(roomKey);
        given(chatRoomService.create(chatRoomCreateDto,user)).willReturn(buildChatRoomResponseDto(roomKey));
        given(chatRoomRepository.save(chatRoomCreateDto.toEntity())).willReturn(chatRoom);
        given(accountRepository.findById(USER_ID)).willReturn(Optional.of(account));

        //when
        ChatRoomCreateResponseDto chatRoomCreateResponseDto=chatRoomApiController.create(chatRoomCreateDto,user);

        //then
        assertThat(chatRoomCreateResponseDto.getRoomKey()).isEqualTo(roomKey);
    }

    @Test
    @DisplayName("채팅방으로 친구 초대")
    void inviteToChatRoom() {
        //given
        String roomKey= UUID.randomUUID().toString().replaceAll("-","");
        Account account=toAccountEntity();
        ChatRoom chatRoom=toChatRoomEntity(roomKey);
        ChatRoomInviteDto chatRoomInviteDto=buildChatRoomInviteDto(roomKey);
        ChatRoomAccount chatRoomAccount= ChatRoomAccount.builder()
                .account(account)
                .chatRoom(chatRoom)
                .build();

        given(chatRoomRepository.findByRoomKey(chatRoomInviteDto.getRoomKey())).willReturn(chatRoom);
        given(accountRepository.findByName(chatRoomInviteDto.getName())).willReturn(account);
        given(chatRoomService.inviteToChatRoom(chatRoomInviteDto,user)).willReturn(chatRoomInviteResponseDto());
        given(chatRoomAccountRepository.save(chatRoomAccount)).willReturn(chatRoomAccount);
        //when

        ChatRoomApiController.Result<ChatRoomInviteResponseDto> result =chatRoomApiController.inviteToChatRoom(buildChatRoomInviteDto(roomKey),user);

        //then
        assertNotNull(result);
    }

    private ChatRoomCreateDto buildChatRoomCreateDto(){
        ChatRoomCreateDto chatRoomCreateDto=new ChatRoomCreateDto();
        chatRoomCreateDto.setTitle("채팅방");
        return chatRoomCreateDto;
    }

    private ChatRoomCreateResponseDto buildChatRoomResponseDto(String roomKey){
        return new ChatRoomCreateResponseDto(roomKey);
    }

    private ChatRoomInviteDto buildChatRoomInviteDto(String roomKey){
        ChatRoomInviteDto chatRoomInviteDto=new ChatRoomInviteDto();
        chatRoomInviteDto.setRoomKey(roomKey);
        chatRoomInviteDto.setName("유저2");
        return chatRoomInviteDto;
    }

    private ChatRoomInviteResponseDto chatRoomInviteResponseDto(){
        return new ChatRoomInviteResponseDto(true);
    }
    private Account toAccountEntity(){
        Account account=Account.builder()
                .name("유저2")
                .email("abc@naver.com")
                .password("123123123")
                .authString("1234556")
                .build();
        return account;
    }

    private ChatRoom toChatRoomEntity(String roomKey){
        return ChatRoom.builder().title("채팅방").roomKey(roomKey).build();
    }

}