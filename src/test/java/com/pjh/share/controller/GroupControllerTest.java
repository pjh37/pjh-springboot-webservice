package com.pjh.share.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {GroupController.class}
,includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
public class GroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GroupRepository postsRepository;

    @MockBean
    private GroupService postService;

    @MockBean
    private GroupAccountService groupAccountService;

    @MockBean
    private AccountService accountService;

    /*
    @BeforeEach
    public void setup(){
        mvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "user",roles = "일반 사용자")
    public void 그룹생성_페이지리턴() throws Exception{
        //임시
        mvc.perform(get("/group-create"))
                .andExpect(status().isOk());
    }
    */
}
