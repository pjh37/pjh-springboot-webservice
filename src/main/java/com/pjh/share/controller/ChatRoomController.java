package com.pjh.share.controller;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    @GetMapping("/chatroom/{roomKey}")
    public String join(Model model, @PathVariable("roomKey")String roomKey,@CurrentUser Account account){
        if(account!=null){
            model.addAttribute("account",account);
        }
        model.addAttribute("roomKey",roomKey);
        return "chatroom";
    }
}
