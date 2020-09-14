package com.pjh.share.domain.chatroomaccount;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.chatroom.ChatRoom;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class ChatRoomAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID")
    private Account account;

    @ManyToOne
    @JoinColumn(name="CHATROOM_ID")
    private ChatRoom chatRoom;

    @Builder
    public ChatRoomAccount(Account account,ChatRoom chatRoom){
        this.account=account;
        this.chatRoom=chatRoom;
    }

}
