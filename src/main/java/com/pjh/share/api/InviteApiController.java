package com.pjh.share.api;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.service.InviteService;
import com.pjh.share.web.dto.InviteAuthWaitDto;
import com.pjh.share.web.dto.InviteRequestDto;
import com.pjh.share.web.dto.InviteResponseDto;
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

    /*
    @GetMapping("/api/v1/friends/auth/list")
    public List<InviteAuthWaitDto> inviteAuthWaitList(@CurrentUser Account account){
        return inviteService.findInviteAuthWaitList(account.getName());
    }
     */
}
