package com.pjh.share.web.dto;

import com.pjh.share.domain.account.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSearchResponseDto {
    private String name;

    public AccountSearchResponseDto(Account entity){
        this.name=entity.getName();
    }
}
