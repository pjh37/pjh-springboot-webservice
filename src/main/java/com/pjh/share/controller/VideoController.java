package com.pjh.share.controller;

import com.pjh.share.common.CurrentUser;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.video.Video;
import com.pjh.share.service.VideoService;
import com.pjh.share.web.dto.VideoResponseDto;
import com.pjh.share.web.dto.VideoUploadRequestDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.result.Output;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @GetMapping("/watch/v/{id}")
    public String video(Model model,@PathVariable Long id,@CurrentUser SessionUser user){
        VideoResponseDto videoResponse =videoService.findById(id);
        if(user!=null){
            model.addAttribute("account",user);
        }
        model.addAttribute("video",videoResponse);
        return "watch";
    }
}
