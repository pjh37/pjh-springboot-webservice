package com.pjh.share.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    @GetMapping("/chatroom/{id}")
    public String join(Model model, @PathVariable("id")Long id){
        model.addAttribute("chatRoomId",id);
        return "chatroom";
    }
}
