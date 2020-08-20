package com.pjh.share.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjh.share.config.SecurityConfig;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.service.AccountService;
import com.pjh.share.service.PostService;
import com.pjh.share.web.dto.PostsResponseDto;

import com.pjh.share.web.dto.PostsSaveRequestDto;
import com.pjh.share.web.dto.PostsUpdateRequestDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {PostApiController.class},includeFilters = @ComponentScan.Filter(classes = {EnableWebSecurity.class}))
public class PostApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void 게시글_저장() throws Exception{
        Posts post= Posts.builder()
                .title("제목")
                .content("내용")
                .clickCount(0L)
                .name("홍길동")
                .groupId(1L)
                .build();

        PostsSaveRequestDto postsSaveRequestDto=new PostsSaveRequestDto();
        postsSaveRequestDto.setTitle("제목");
        postsSaveRequestDto.setName("홍길동");
        postsSaveRequestDto.setGroupId(0L);
        postsSaveRequestDto.setContent("내용");

        SessionUser sessionUser= SessionUser.builder()
                .id(0L)
                .name("홍길동")
                .email("abcdef@gmail.com")
                .build();

        given(postApiController.save(postsSaveRequestDto,sessionUser)).willReturn(1L);

        String content=objectMapper.writeValueAsString(postsSaveRequestDto);

        mvc.perform(post("/api/post")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
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
        post.setId(0L);
        PostsResponseDto postsResponseDto=new PostsResponseDto(post);

        given(postApiController.findById(post.getId())).willReturn(postsResponseDto);

        mvc.perform(get("/api/post/{id}",post.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("제목"))
                .andExpect(jsonPath("content").value("내용"));


    }

    @Test
    public void 게시글_수정() throws Exception{
        Posts post= Posts.builder()
                .title("제목")
                .content("내용")
                .clickCount(0L)
                .name("홍길동")
                .groupId(1L)
                .build();
        post.setId(0L);
        /*
        Long savedId=postService.save(post);
        PostsResponseDto postsResponseDto=new PostsResponseDto(post);
        given(postApiController.findById(savedId)).willReturn(postsResponseDto);

        PostsUpdateRequestDto postsUpdateRequestDto=new PostsUpdateRequestDto();
        postsUpdateRequestDto.setTitle("바뀐제목");
        postsUpdateRequestDto.setContent("바뀐내용");

        given(postApiController.update(savedId,postsUpdateRequestDto)).willReturn(savedId);

        mvc.perform(get("/api/post/{id}",savedId)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("바뀐제목"))
                .andExpect(jsonPath("content").value("바뀐내용"));

         */
    }
}
