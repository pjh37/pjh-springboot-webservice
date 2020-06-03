package com.pjh.share.domain.groupaccount;

import com.pjh.share.domain.BaseTimeEntity;
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
public class GroupAccount extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID")
    private Account account;

    @ManyToOne
    @JoinColumn(name="GROUPS_ID")
    private Group group;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @Builder
    public GroupAccount(Account account,Group group,Role role){
        this.account=account;
        this.group=group;
        this.role=role;
    }

}
