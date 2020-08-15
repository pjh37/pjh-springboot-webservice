package com.pjh.share.web.dto;

import com.pjh.share.domain.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SessionUser implements Serializable {
    //private static final long serialVersionUID = 1L;
    private String name;
    private String email;

    public SessionUser(Account account){
        this.name=account.getName();
        this.email=account.getEmail();
    }
}
