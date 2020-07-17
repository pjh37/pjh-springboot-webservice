package com.pjh.share.controller;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/chatroom/{roomKey}")
    public String join(Model model, @PathVariable("roomKey")String roomKey,@CurrentUser Account account){
        if(account!=null){
            model.addAttribute("account",account);
        }
        model.addAttribute("roomKey",roomKey);
        return "chatroom";
    }

    @GetMapping("/chatroom-list")
    public String chatRoom(Model model,@CurrentUser Account account){
        if(account!=null){
            model.addAttribute("account",account);
            model.addAttribute("chatRooms",chatRoomService.findAllMyChatRoom(account.getId()));
        }
        return "chatroom-list";
    }
}
