package com.pjh.share.controller;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.service.CommentService;
import com.pjh.share.service.GroupService;
import com.pjh.share.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final GroupService groupService;
    private final CommentService commentService;
    private final Integer firstPage=0;

    @GetMapping("/post/save/{groupId}")
    public String postSave(Model model, @PathVariable("groupId") Long groupId, @CurrentUser SessionUser user){
        if(user!=null){
            model.addAttribute("account",user);
        }
        model.addAttribute("group",groupService.findById(groupId));
        return "post-save";
    }

    @GetMapping("/post/read/{groupId}/{postId}")
    public String postRead(Model model,@PathVariable("groupId") Long groupId,
                           @PathVariable("postId") Long postId,@CurrentUser SessionUser user){
        postService.postClicked(postId);
        model.addAttribute("groupId",groupId);
        model.addAttribute("post",postService.findById(postId));
        model.addAttribute("comments",commentService.findAllDesc(firstPage,postId));
        if(user!=null){
            model.addAttribute("account",user);
        }
        return "post-read";
    }
}
