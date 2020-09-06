package com.pjh.share.api;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.groupaccount.GroupAccount;
import com.pjh.share.service.GroupAccountService;
import com.pjh.share.service.GroupService;
import com.pjh.share.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class GroupApiController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private final GroupService groupService;

    @PostMapping("/api/group/join")
    public Boolean join(Model model,@RequestBody GroupJoinRequestDto requestDto, @CurrentUser SessionUser user)throws Exception{
        groupService.join(requestDto,user);
        if(user!=null){
            model.addAttribute("account",user);
        }
        return true;
    }

    @PostMapping("/api/group/check/p")
    public boolean groupPasswordCheck(@RequestBody GroupPwCheckRequestDto requestDto){
        System.out.println("groupPasswordCheck : "+requestDto.getId()+" pw : "+requestDto.getPassword());
        if(groupService.groupPwCheck(requestDto)){
            return true;
        }
        return false;
    }

    @GetMapping("/api/group/memberCheck/{groupId}")
    public boolean groupMemberCheck(@PathVariable Long groupId,@CurrentUser SessionUser user){
        return groupService.groupMemberCheck(groupId,user);
    }
}
