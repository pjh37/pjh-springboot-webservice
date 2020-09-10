package com.pjh.share.controller;

import com.pjh.share.domain.account.Role;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.group.Group;
import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.service.AccountService;
import com.pjh.share.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {PostController.class}
        ,includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
public class PostControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    private MockHttpSession session;

    @MockBean
    private AccountService accountService;

    @MockBean
    private PostService postService;

    @MockBean
    private PostController postController;

    @BeforeEach
    public void setUp(){
        mvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        session=new MockHttpSession();
        SessionUser user= SessionUser.builder()
                .name("user")
                .role(Role.USER)
                .build();
        session.setAttribute("user",user);
    }

    @Test
    @WithMockUser(username = "user",roles = "일반 사용자")
    public void 게시물_저장_페이지() throws Exception{
        Long groupId=1L;
        String title="스프링 부트 스터디 모집합니다";
        String des="선착순모집입니다.";
        Integer totalNum=10;
        Group group=Group.builder()
                .title(title)
                .description(des)
                .totalNum(totalNum)
                .build();

        mvc.perform(get("/post/save/{groupId}",groupId).session(session))
                .andExpect(status().isOk());
    }
}
