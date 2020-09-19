package com.pjh.share.controller;

import com.pjh.share.api.PostApiController;
import com.pjh.share.domain.post.Posts;
import com.pjh.share.service.AccountService;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.service.PostService;
import com.pjh.share.web.dto.PostsListResponseDto;
import com.pjh.share.web.dto.PostsResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {TestController.class}
        ,includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
public class TestControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TestController testController;

    @MockBean
    private AccountService accountService;

    @BeforeEach
    public void setUp(){
        mvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    public void 게시물_조회()throws Exception{
        mvc.perform(get("/uploadTest"))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
