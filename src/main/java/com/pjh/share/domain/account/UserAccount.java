package com.pjh.share.domain.account;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.List;

public class UserAccount extends User {
    public UserAccount(Account account){
        super(account.getEmail(),account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+account.getRole())));
    }
}
