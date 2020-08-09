package com.pjh.share.domain.friend;

import com.pjh.share.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class InviteAuthWait {
    @Id
    @GeneratedValue
    private Long id;

    private String sender;

    private String receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    @Builder
    public InviteAuthWait(Account sender, Account receiver, State state){
        this.sender=sender.getName();
        this.receiver=receiver.getName();
        this.state=state;
    }
}
