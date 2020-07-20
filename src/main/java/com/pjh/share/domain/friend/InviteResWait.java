package com.pjh.share.domain.friend;

import com.pjh.share.domain.account.Account;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class InviteResWait {
    @Id
    @GeneratedValue
    private Long id;

    private Long senderId;

    private Long receiverId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Builder
    public InviteResWait(Account senderId,Account receiverId,Status status){
        this.senderId=senderId.getId();
        this.receiverId=receiverId.getId();
        this.status=status;
    }
}
