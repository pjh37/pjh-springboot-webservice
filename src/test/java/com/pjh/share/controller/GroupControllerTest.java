package com.pjh.share.controller;


import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.service.AccountService;
import com.pjh.share.service.GroupAccountService;
import com.pjh.share.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {GroupController.class},includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
public class GroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private GroupRepository postsRepository;

    @MockBean
    private GroupService postService;

    @MockBean
    private GroupAccountService groupAccountService;

    @MockBean
    private AccountService accountService;

    @BeforeEach
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
