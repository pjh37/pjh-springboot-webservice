package com.pjh.share.api;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.friend.InviteResWait;
import com.pjh.share.service.InviteService;
import com.pjh.share.web.dto.ChatRoomInviteDto;
import com.pjh.share.web.dto.InviteRequestDto;
import com.pjh.share.web.dto.InviteResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InviteApiController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private final InviteService inviteService;
    @PostMapping("/api/friend/invite")
    public InviteResponseDto friendInvite(@RequestBody InviteRequestDto req, @CurrentUser Account account){
        boolean result=inviteService.inviteRequest(account,req);
        logger.info("=================");
        logger.info("/api/friend/invite "+req.getName());
        logger.info("=================");
        return new InviteResponseDto(result);
    }
}
