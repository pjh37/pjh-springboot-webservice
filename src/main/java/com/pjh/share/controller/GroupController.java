package com.pjh.share.controller;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.service.GroupAccountService;
import com.pjh.share.service.GroupService;
import com.pjh.share.web.dto.GroupCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class GroupController {
    private final GroupService groupService;
    private final GroupAccountService groupAccountService;

    @PostMapping("/api/group")
    public String save(Model model, GroupCreateRequestDto requestDto, @CurrentUser Account account) throws Exception{
        groupService.save(requestDto,account);
        if(account!=null){
            model.addAttribute("account",account);
        }
        return "index";
    }

    @GetMapping("/api/withdraw")
    public String withdrawGroup(@RequestParam(value = "group",defaultValue = "-1")Long groupId, @CurrentUser Account account){
        groupAccountService.withdrawGroup(account.getId(),groupId);
        return "redirect:/";
    }
}
