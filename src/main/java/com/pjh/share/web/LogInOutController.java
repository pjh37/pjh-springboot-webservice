package com.pjh.share.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInOutController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
