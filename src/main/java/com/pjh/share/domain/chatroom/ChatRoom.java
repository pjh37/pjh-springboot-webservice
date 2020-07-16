package com.pjh.share.domain.chatroom;

import com.pjh.share.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String roomKey;

    @Builder
    public ChatRoom(String title,String roomKey){
        this.title=title;
        this.roomKey=roomKey;
    }
}
