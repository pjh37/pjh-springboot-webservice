package com.pjh.share.repository;

import com.pjh.share.domain.chatroom.ChatRoom;
import com.pjh.share.domain.chatroom.ChatRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ChatRoomRepositoryTest {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @BeforeEach
    public void cleanUp(){ }

    @Test
    public void 채팅방생성_조회(){
        String title="스프링 스터디 하실분";
        String roomKey= UUID.randomUUID().toString();
        ChatRoom chatRoom=ChatRoom.builder()
                .title(title)
                .roomKey(roomKey)
                .build();

        ChatRoom savedChatRoom=chatRoomRepository.save(chatRoom);

        ChatRoom findChatRoom=chatRoomRepository.findById(savedChatRoom.getId()).get();

        assertThat(findChatRoom.getTitle()).isEqualTo(title);
        assertThat(findChatRoom.getRoomKey()).isEqualTo(roomKey);
    }

    @Test
    public void 채팅방생성_업데이트(){
        String title="스프링 스터디 하실분";
        String roomKey= UUID.randomUUID().toString();
        String updatedTitle="토익스터디";
        String updatedRoomKey=UUID.randomUUID().toString();
        ChatRoom chatRoom=ChatRoom.builder()
                .title(title)
                .roomKey(roomKey)
                .build();

        ChatRoom savedChatRoom=chatRoomRepository.save(chatRoom);

        ChatRoom findChatRoom=chatRoomRepository.findById(savedChatRoom.getId()).get();
        findChatRoom.setTitle(updatedTitle);
        findChatRoom.setRoomKey(updatedRoomKey);

        ChatRoom updatedChatRoom=chatRoomRepository.findById(savedChatRoom.getId()).get();

        assertThat(updatedChatRoom.getTitle()).isEqualTo(updatedTitle);
        assertThat(updatedChatRoom.getRoomKey()).isEqualTo(updatedRoomKey);
    }

    @Test
    public void 채팅방생성_삭제(){
        String title="스프링 스터디 하실분";
        String roomKey= UUID.randomUUID().toString();
        ChatRoom chatRoom=ChatRoom.builder()
                .title(title)
                .roomKey(roomKey)
                .build();

        ChatRoom savedChatRoom=chatRoomRepository.save(chatRoom);
        assertThat(chatRoomRepository.findAll().size()).isEqualTo(1);
        chatRoomRepository.delete(savedChatRoom);
        assertThat(chatRoomRepository.findAll().size()).isEqualTo(0);
    }
}
