package com.pjh.share.domain.chatroomaccount;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.chatroom.ChatRoom;

import javax.persistence.*;

@Entity
public class ChatRoomAccount {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID")
    private Account account;

    @ManyToOne
    @JoinColumn(name="CHATROOM_ID")
    private ChatRoom chatRoom;
}
