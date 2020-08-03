package com.pjh.share.api;


import com.pjh.share.service.AccountService;
import com.pjh.share.component.EmailSender;
import com.pjh.share.web.dto.AccountCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
}
