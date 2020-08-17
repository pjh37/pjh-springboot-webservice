package com.pjh.share.api;

import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.service.AccountService;
import com.pjh.share.service.PostService;
import com.pjh.share.web.dto.PostsResponseDto;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostApiControllerTest.class)
public class PostApiControllerTest {

    @Autowired
    MockMvc mvc;



    @MockBean
    private PostsRepository postsRepository;

    @MockBean
    private PostService postService;





    @Test
    public void 게시글_조회() throws Exception{
         Posts post= Posts.builder()
                .title("제목")
                .content("내용")
                .clickCount(0L)
                .name("홍길동")
                .groupId(1L)
                .build();
         /*
        PostsResponseDto postsResponseDto=new PostsResponseDto(post);
        given(postService.findById(1L)).willReturn(postsResponseDto);

        final ResultActions actions=mvc.perform(get("/api/post/{id}",1L)
        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
        //id,name,title,content
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("title").value("제목"))
                .andExpect(jsonPath("content").value("내용"));

          */
    }
}
