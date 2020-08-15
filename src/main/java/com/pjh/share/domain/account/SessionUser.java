package com.pjh.share.domain.account;

import com.pjh.share.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@RedisHash("user")
public class SessionUser implements Serializable {

    @Id
    private Long id;
    private String name;
    private String email;

    @Builder
    public SessionUser(Long id,String name,String email){
        this.id=id;
        this.name=name;
        this.email=email;
    }
}
