package com.pjh.share.domain.friend;

import com.pjh.share.domain.BaseTimeEntity;
import com.pjh.share.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name="friends")
public class Friend extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long accountId;

    private Long friendId;

    @Builder
    public Friend(Long accountId,Long friendId){
        this.accountId=accountId;
        this.friendId=friendId;
    }
}
