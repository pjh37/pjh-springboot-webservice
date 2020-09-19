package com.pjh.share.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.account.Role;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.group.Group;
import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.service.AccountService;
import com.pjh.share.service.PostService;
import com.pjh.share.web.dto.PostsListResponseDto;
import com.pjh.share.web.dto.PostsResponseDto;

import com.pjh.share.web.dto.PostsSaveRequestDto;
import com.pjh.share.web.dto.PostsUpdateRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

/*
WebMvcTest의 경우 JPA의 기능이 작동하지 않는다.
 */

@SpringBootTest
@AutoConfigureMockMvc
public class PostApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostApiController postApiController;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AccountRepository accountRepository;

    private SessionUser user;

    @BeforeEach
    public void setup(){
        postsRepository.deleteAll();
        mvc= MockMvcBuilders.webAppContextSetup(context).addFilter(((request,response,chain)->{
            response.setCharacterEncoding("UTF-8");
            chain.doFilter(request,response);
        }))
                .apply(springSecurity())
                .build();
        user=SessionUser.builder()
                .name("user")
                .role(Role.USER)
                .id(1L)
                .build();
    }
    @Test
    public void 게시글_저장() throws Exception{
        //given
        accountRepository.save(Account.builder().name("유저").password("123")
                .email("abc").role(Role.USER).authString("abc").build());
        Posts post=getPost();
        post.setId(0L);
        Group group=getGroup();
        groupRepository.save(group);
        post.setGroup(group);



        //when
        Long postId=postApiController.save(buildPostRequest(post),user);
        PostsResponseDto postsResponseDto=postApiController.findById(postId);

        //then
        assertThat(postsResponseDto.getTitle()).isEqualTo("제목");


    }

    @Test
    public void 게시글_조회() throws Exception{
        //given
        Posts post=getPost();
        Long postId=postsRepository.save(post).getId();

        //when
        PostsResponseDto postsResponseDto=postApiController.findById(postId);

        //then
        assertThat(postsResponseDto.getTitle()).isEqualTo("제목");
        assertThat(postsResponseDto.getContent()).isEqualTo("내용");
    }

    @Test
    public void 게시글_수정() throws Exception{
        //given
        Posts post=getPost();
        Long postId=postsRepository.save(post).getId();
        postApiController.update(postId,buildPostUpdateRequest());

        //when
        PostsResponseDto postsResponseDto=postApiController.findById(postId);

        //then
        assertThat(postsResponseDto.getTitle()).isEqualTo("바뀐제목");
        assertThat(postsResponseDto.getContent()).isEqualTo("바뀐내용");
    }
    private ResultActions requestGetPost()throws Exception{
        return mvc.perform(get("/api/post/{id}",0L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

    }

    private PostsSaveRequestDto buildPostRequest(Posts post){
        PostsSaveRequestDto postsSaveRequestDto=new PostsSaveRequestDto();
        postsSaveRequestDto.setTitle(post.getTitle());
        postsSaveRequestDto.setName(post.getName());
        postsSaveRequestDto.setGroupId(post.getGroup().getId());
        postsSaveRequestDto.setContent(post.getContent());
        return postsSaveRequestDto;
    }

    private PostsUpdateRequestDto buildPostUpdateRequest(){
        PostsUpdateRequestDto postsUpdateRequestDto=new PostsUpdateRequestDto();
        postsUpdateRequestDto.setTitle("바뀐제목");
        postsUpdateRequestDto.setContent("바뀐내용");
        return postsUpdateRequestDto;
    }

    private Posts getPost(){
        return Posts.builder()
                .title("제목")
                .content("내용")
                .clickCount(0L)
                .name("홍길동")
                .groupId(1L)
                .build();
    }

    private Posts getUpdatedPost(){
        Posts post= Posts.builder()
                .title("바뀐제목")
                .content("바뀐내용")
                .clickCount(0L)
                .name("홍길동")
                .groupId(1L)
                .build();
        post.setId(0L);
        return post;
    }

    private Group getGroup(){
        return Group.builder()
                .title("그룹제목")
                .description("설명")
                .totalNum(10)
                .build();
    }
}
