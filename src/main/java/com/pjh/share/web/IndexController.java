package com.pjh.share.web;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.Role;
import com.pjh.share.service.AccountService;
import com.pjh.share.service.GroupService;
import com.pjh.share.service.PostService;
import com.pjh.share.web.dto.GroupListResponseDto;
import com.pjh.share.web.dto.GroupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostService postService;
    private final GroupService groupService;
    private final AccountService accountService;
    @GetMapping("/")
    public String index(Model model, @CurrentUser Account account){
        if(account!=null){
            model.addAttribute("name",account.getName());
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
            model.addAttribute("name",account.getName());
        }
        return "group-manage";
    }
    @GetMapping("/group-create")
    public String group_create(Model model, @CurrentUser Account account){
        if(account!=null){
            model.addAttribute("name",account.getName());
        }
        return "group-create";
    }

    @GetMapping("/group/{id}")
    public String group_read(Model model,@PathVariable Long id,@CurrentUser Account account){
        if(account!=null){
            model.addAttribute("name",account.getName());
        }
        model.addAttribute("group",groupService.findById(id));
        model.addAttribute("posts",postService.findAllDesc(0));
        return "group-read";
    }


}
