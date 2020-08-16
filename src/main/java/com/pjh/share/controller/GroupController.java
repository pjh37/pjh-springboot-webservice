package com.pjh.share.controller;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.service.GroupAccountService;
import com.pjh.share.service.GroupService;
import com.pjh.share.web.dto.GroupCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class GroupController {
    private final GroupService groupService;
    private final GroupAccountService groupAccountService;

    @PostMapping("/api/group")
    public String save(Model model, GroupCreateRequestDto requestDto, @CurrentUser SessionUser user) throws Exception{
        groupService.save(requestDto,user);
        if(user!=null){
            model.addAttribute("account",user);
        }
        return "index";
    }

    @GetMapping("/api/withdraw")
    public String withdrawGroup(@RequestParam(value = "group",defaultValue = "-1")Long groupId, @CurrentUser SessionUser user){
        groupAccountService.withdrawGroup(user.getId(),groupId);
        return "redirect:/";
    }
}
