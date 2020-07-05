package com.pjh.share.controller;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.Role;
import com.pjh.share.domain.group.Group;
import com.pjh.share.service.AccountService;
import com.pjh.share.service.GroupService;
import com.pjh.share.service.PostService;
import com.pjh.share.service.VideoService;
import com.pjh.share.web.dto.GroupListResponseDto;
import com.pjh.share.web.dto.GroupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostService postService;
    private final GroupService groupService;
    private final AccountService accountService;
    private final VideoService videoService;
    @GetMapping("/")
    public String index(Model model, @CurrentUser Account account){
        if(account!=null){
            model.addAttribute("account",account);

        }
        List<GroupListResponseDto> groupListResponseDtos=groupService.findAll();
        model.addAttribute("groups",groupListResponseDtos);
        return "index";
    }
    @GetMapping("/email/auth/{auth}")
    public String emailAuth(@PathVariable String auth){
        String userEmail=accountService.findByAuth(auth);
        accountService.setRole(userEmail, Role.USER);
        return "welcome";
    }
    @GetMapping("/group-manage")
    public String group_manage(Model model, @CurrentUser Account account){
        if(account!=null){
            model.addAttribute("account",account);
        }
        return "group-manage";
    }
    @GetMapping("/group-create")
    public String group_create(Model model, @CurrentUser Account account){
        if(account!=null){
            model.addAttribute("account",account);
        }
        return "group-create";
    }

    @GetMapping("/group/{id}")
    public String group_read(Model model, @PathVariable Long id,
                             @RequestParam(value = "page",defaultValue = "1")Integer pageNum, @CurrentUser Account account){
        if(account!=null){
            model.addAttribute("account",account);
        }
        model.addAttribute("members",groupService.findGroupMemberByGroupId(id));
        model.addAttribute("videos",videoService.findAllDesc(id));
        model.addAttribute("group",groupService.findById(id));
        model.addAttribute("posts",postService.findAllDesc(pageNum,id));
        model.addAttribute("pageList",postService.getPageList(pageNum,id));
        return "group-read";
    }

    @GetMapping("/myGroups")
    public String myGroups(Model model,@CurrentUser Account account){
        List<GroupListResponseDto> myGroups=groupService.findAllMyGroup(account.getId());
        if(account!=null){
            model.addAttribute("account",account);
        }
        model.addAttribute("myGroups",myGroups);
        return "myGroups";
    }
}
