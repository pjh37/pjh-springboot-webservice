package com.pjh.share.web;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.service.GroupService;
import com.pjh.share.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostService postService;
    private final GroupService groupService;

    @GetMapping("/post/save/{groupId}")
    public String postSave(Model model, @PathVariable Long groupId, @CurrentUser Account account){
        if(account!=null){
            model.addAttribute("name",account.getName());
        }
        model.addAttribute("group",groupService.findById(groupId));
        return "post-save";
    }

    @GetMapping("/post/read/{groupId}/{postId}")
    public String postRead(Model model,@PathVariable Long groupId, @PathVariable Long postId,@CurrentUser Account account){
        model.addAttribute("groupId",groupId);
        model.addAttribute("post",postService.findById(postId));

        if(account!=null){
            model.addAttribute("name",account.getName());
        }
        return "post-read";
    }
}
