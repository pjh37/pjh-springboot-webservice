package com.pjh.share.web.dto;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountCreateRequestDto {
    private String name;
    private String email;
    private String password;
    private String authString;
    private Role role;
    public Account toEntity(){
        return Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .authString(authString)
                .role(Role.GUEST)
                .build();
    }

}
