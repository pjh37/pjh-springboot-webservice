package com.pjh.share.domain.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.List;

public class UserAccount extends User {
    private Logger logger= LoggerFactory.getLogger(getClass());
    private Account account;
    public UserAccount(Account account){
        super(account.getEmail(),account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+account.getRole())));
        this.account=account;
        logger.info("======================");
        logger.info("UserAccount 동작");
        logger.info("======================");
    }

    public Account getAccount(){
        return account;
    }
}
