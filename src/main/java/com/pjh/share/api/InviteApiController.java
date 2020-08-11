package com.pjh.share.api;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.service.InviteService;
import com.pjh.share.web.dto.InviteAuthWaitDto;
import com.pjh.share.web.dto.InviteRequestDto;
import com.pjh.share.web.dto.InviteResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InviteApiController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private final InviteService inviteService;
    @PostMapping("/api/v1/friend/invite")
    public InviteResponseDto friendInvite(@RequestBody InviteRequestDto req, @CurrentUser Account account){
        boolean result=inviteService.inviteRequest(account,req);
        logger.info("=================");
        logger.info("/api/friend/invite "+req.getName());
        logger.info("=================");
        return new InviteResponseDto(result);
    }

    @PostMapping("/api/v1/invite/confirm/{id}")
    public Result inviteConfirm(@PathVariable("id")Long id){
        inviteService.inviteConfirm(id);
        return new Result(true);
    }

    @PostMapping("/api/v1/invite/refuse/{id}")
    public Result inviteRefuse(@PathVariable("id")Long id){

        return new Result(true);
    }

    @GetMapping("/api/v1/invite/list")
    public Result inviteList(@CurrentUser Account account){
        List<InviteAuthWaitDto> inviteAuthWaitDtos=inviteService.findInviteAuthWaitList(account.getName());
        return new Result(inviteAuthWaitDtos);
    }


    @Data
    @AllArgsConstructor
    class Result<T>{
        private T data;
    }
}
