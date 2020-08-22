package com.pjh.share.domain.account;

import com.pjh.share.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public SessionUser(Long id,String name,String email,Role role){
        this.id=id;
        this.name=name;
        this.email=email;
        this.role=role;
    }
}
