package com.pjh.share.api;


import com.pjh.share.domain.account.Account;
import com.pjh.share.service.AccountService;
import com.pjh.share.component.EmailSender;
import com.pjh.share.web.dto.AccountCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
public class AccountApiController {
    private final AccountService accountService;
    private final EmailSender emailSender;
    @PostMapping("/join")
    public Long save(@RequestBody AccountCreateRequestDto req) throws Exception{
        String authString= UUID.randomUUID().toString();
        req.setAuthString(authString);
        emailSender.sendAuthMail(req.getEmail(),authString);
        return accountService.save(req);
    }

    @GetMapping("/api/v1/search")
    public Result findByNameContaining(@RequestParam(value = "name")String name){
        return new Result(accountService.findByNameContaining(name));
    }

    @Data
    @AllArgsConstructor
    class Result<T>{
        private T data;
    }
}
