package com.pjh.share.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.web.dto.GroupCreateRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroupControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupRepository groupRepository;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void 그룹생성_페이지리턴() throws Exception{
        //임시
        mvc.perform(get("/group-create"))
                .andExpect(status().isOk());
        /*
        String index="index";
        String title="스프링 부트 스터디 모집";
        String des="선착순모집입니다.";
        Integer totalNum=10;
        GroupCreateRequestDto requestDto=new GroupCreateRequestDto();
        requestDto.setTitle(title);
        requestDto.setDescription(des);
        requestDto.setTotalNum(10);
        requestDto.setCurrentNum(0);
        String content=objectMapper.writeValueAsString(requestDto);
        mvc.perform(post("/api/group")
        .content(content)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

         */
    }
}
