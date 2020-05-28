package com.pjh.share.web;

import com.pjh.share.service.GroupService;
import com.pjh.share.web.dto.GroupCreateRequestDto;
import com.pjh.share.web.dto.GroupPwCheckRequestDto;
import com.pjh.share.web.dto.GroupResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class GroupApiController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private final GroupService groupService;

    @PostMapping("/api/group")
    public String save(GroupCreateRequestDto requestDto) throws Exception{
        groupService.save(requestDto);
        return "index";
    }

    @PostMapping("/api/group/check/p")
    @ResponseBody
    public boolean groupPasswordCheck(@RequestBody GroupPwCheckRequestDto requestDto){
        System.out.println("groupPasswordCheck : "+requestDto.getId()+" pw : "+requestDto.getPassword());
        if(groupService.groupPwCheck(requestDto)){
            return true;
        }
        return false;
    }

}
