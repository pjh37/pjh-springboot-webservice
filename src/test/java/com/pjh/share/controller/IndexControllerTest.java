package com.pjh.share.controller;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.account.Role;

import com.pjh.share.service.AccountService;
import com.pjh.share.service.GroupService;
import com.pjh.share.service.PostService;
import com.pjh.share.service.VideoService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {IndexController.class},includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
public class IndexControllerTest {
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AccountService accountService;

    @MockBean
    private PostService postService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private VideoService videoService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup(){
        mvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void index_페이지리턴() throws Exception{
        String index="index";

        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void 이메일인증_완료()throws Exception{

        String name="홍길동";
        String email="jjjj1352@naver.com";
        String password="123123123";
        String authString= UUID.randomUUID().toString();

        accountRepository.save(Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .authString(authString)
                .role(Role.GUEST).build());

        mvc.perform(get("/email/auth/{auth}",authString))
                .andExpect(status().isOk());
    }

    @Test
    public void 그룹관리페이지()throws Exception{
        mvc.perform(get("/group-manage"))
                .andExpect(status().isOk());
    }

    @Test
    public void 그룹생성페이지()throws Exception{
        mvc.perform(get("/group-create"))
                .andExpect(status().isOk());
    }
}
