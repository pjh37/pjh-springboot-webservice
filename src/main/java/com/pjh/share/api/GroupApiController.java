package com.pjh.share.api;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.service.GroupService;
import com.pjh.share.web.dto.GroupCreateRequestDto;
import com.pjh.share.web.dto.GroupJoinRequestDto;
import com.pjh.share.web.dto.GroupPwCheckRequestDto;
import com.pjh.share.web.dto.GroupResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class GroupApiController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private final GroupService groupService;

    @PostMapping("/api/group")
    @ResponseBody
    public String save(Model model, GroupCreateRequestDto requestDto, @CurrentUser Account account) throws Exception{
        groupService.save(requestDto,account);
        if(account!=null){
            model.addAttribute("account",account);
        }

        return "index";
    }

    @PostMapping("/api/group/join")
    @ResponseBody
    public Boolean join(Model model,@RequestBody GroupJoinRequestDto requestDto, @CurrentUser Account account)throws Exception{
        System.out.println("groupId : "+requestDto.getGroupId());
        groupService.join(requestDto,account);
        if(account!=null){
            model.addAttribute("account",account);
        }
        return true;
    }


    @PostMapping("/api/group/check/p")
    @ResponseBody
    public boolean groupPasswordCheck(@RequestBody GroupPwCheckRequestDto requestDto){
        System.out.println("groupPasswordCheck : "+requestDto.getId()+" pw : "+requestDto.getPassword());
        if(groupService.groupPwCheck(requestDto)){
            return true;
        }
        return false;
    }

    @GetMapping("/api/group/memberCheck/{groupId}")
    @ResponseBody
    public boolean groupMemberCheck(@PathVariable Long groupId,@CurrentUser Account account){
        return groupService.groupMemberCheck(groupId,account);
    }

}
