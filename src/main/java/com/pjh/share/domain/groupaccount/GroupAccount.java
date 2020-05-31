package com.pjh.share.domain.groupaccount;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.group.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GroupAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID")
    private Account account;

    @ManyToOne
    @JoinColumn(name="GROUPS_ID")
    private Group group;

    @Builder
    public GroupAccount(Account account,Group group){
        this.account=account;
        this.group=group;
    }

}
