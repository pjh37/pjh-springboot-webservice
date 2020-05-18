package com.pjh.share.web;

import com.pjh.share.service.GroupService;
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
    private final GroupService groupService;

    @GetMapping("/")
    public String index(Model model){
        List<GroupListResponseDto> groupListResponseDtos=groupService.findAll();
        model.addAttribute("groups",groupListResponseDtos);
        return "index";
    }

    @GetMapping("/group-create")
    public String group_create(){
        return "group-create";
    }

    @GetMapping("/group/{id}")
    public String group_read(Model model,@PathVariable Long id){
        model.addAttribute("group",groupService.findById(id));
        return "group-read";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
}
