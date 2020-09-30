package com.pjh.share.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjh.share.service.AccountService;

import com.pjh.share.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = {PostController.class}
        ,includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
public class PostControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MockHttpSession session;

    @MockBean
    private AccountService accountService;

    @MockBean
    private PostService postService;

    @MockBean
    private PostController postController;

    /*
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

        mvc.perform(get("/post/save/{groupId}",0L))
                .andExpect(status().isOk());
    }

     */
}
