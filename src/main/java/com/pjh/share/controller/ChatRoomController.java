package com.pjh.share.controller;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.friend.InviteAuthWait;
import com.pjh.share.service.ChatRoomService;
import com.pjh.share.service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final InviteService inviteService;
    @GetMapping("/chatroom/{roomKey}")
    public String join(Model model, @PathVariable("roomKey")String roomKey,@CurrentUser SessionUser user){
        if(user!=null){
            model.addAttribute("account",user);
        }
        model.addAttribute("roomKey",roomKey);
        return "chatroom";
    }

    @GetMapping("/chatroom-list")
    public String chatRoom(Model model,@CurrentUser SessionUser user){
        if(user!=null){
            model.addAttribute("account",user);
            model.addAttribute("chatRooms",chatRoomService.findAllMyChatRoom(user.getId()));
            //model.addAttribute("authRequests",inviteService.findInviteAuthWaitList(account.getName()));
        }
        return "chatroom-list";
    }
}
