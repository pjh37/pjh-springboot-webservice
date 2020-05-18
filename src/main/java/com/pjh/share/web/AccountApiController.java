package com.pjh.share.web;

import com.pjh.share.service.AccountService;
import com.pjh.share.web.dto.AccountCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountApiController {
    private final AccountService accountService;
    @PostMapping("/join")
    public Long save(@RequestBody AccountCreateRequestDto req){
        System.out.println(req.getName());
        System.out.println(req.getEmail());
        return accountService.save(req);
    }
}
