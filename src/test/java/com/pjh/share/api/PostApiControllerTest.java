package com.pjh.share.api;

import com.pjh.share.config.SecurityConfig;
import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.service.AccountService;
import com.pjh.share.service.PostService;
import com.pjh.share.web.dto.PostsResponseDto;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {PostApiController.class},includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
public class PostApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private PostsRepository postsRepository;

    @MockBean
    private PostService postService;

    @MockBean
    private PostApiController postApiController;
    @MockBean
    private AccountService accountService;

    @MockBean
    private PostsResponseDto postsResponseDto;


    @BeforeEach
    public void setup(){
        postsRepository.deleteAll();
        mvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    public void 게시글_조회() throws Exception{
        Posts post= Posts.builder()
                .title("제목")
                .content("내용")
                .clickCount(0L)
                .name("홍길동")
                .groupId(1L)
                .build();
        Long savedId=postService.save(post);
        PostsResponseDto postsResponseDto=new PostsResponseDto(post);
        given(postApiController.findById(savedId)).willReturn(postsResponseDto);

        mvc.perform(get("/api/post/{id}",savedId)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("제목"));
         /*
        postService.save(post);
        when().thenReturn(post);
        //given(post).willReturn(post);


        final ResultActions actions=mvc.perform(get("/api/post/{id}",1L)
        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
        //id,name,title,content
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))//jsonPath("id").value(1L)
                .andExpect(jsonPath("title").value("제목"))
                .andExpect(jsonPath("content").value("내용"));


         */

    }
}
