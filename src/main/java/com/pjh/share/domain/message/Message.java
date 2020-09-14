package com.pjh.share.domain.message;

import com.pjh.share.domain.BaseTimeEntity;
import com.pjh.share.domain.chatroom.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Message extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="CHATROOM_ID")
    private ChatRoom chatRoom;

    private String name;

    private String content;

    @Builder
    public Message(ChatRoom chatRoom,String name,String content){
        this.chatRoom=chatRoom;
        this.name=name;
        this.content=content;
    }
}
