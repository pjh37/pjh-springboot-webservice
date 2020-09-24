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
import com.pjh.share.service.post.PostRepository;
import com.pjh.share.web.dto.PostsListResponseDto;
import com.pjh.share.web.dto.PostsResponseDto;

import com.pjh.share.web.dto.PostsSaveRequestDto;
import com.pjh.share.web.dto.PostsUpdateRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
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
@ExtendWith(SpringExtension.class)
public class PostApiControllerTest {
    private static final Long USER_ID=1L;
    private static final Long POST_ID=1L;

    private PostApiController postApiController;

    @Mock
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    private SessionUser user;


    @BeforeEach
    public void setup(){
        postApiController=new PostApiController(postService);
        user=SessionUser.builder()
                .name("user")
                .role(Role.USER)
                .id(USER_ID)
                .build();
    }


    @Test
    public void 게시글_저장() throws Exception{
        //given

        Posts post=getPost();
        post.setGroup(getGroup());
        //when
        when(postApiController.save(buildPostRequest(post),user)).thenReturn(POST_ID);
        when(postApiController.findById(POST_ID)).thenReturn(buildPostResponseDto(post));
        PostsResponseDto postsResponseDto=postApiController.findById(POST_ID);

        //then
        assertThat(postsResponseDto.getTitle()).isEqualTo("제목");
    }

    @Test
    public void 게시글_조회() throws Exception{
        //given
        postApiController=new PostApiController(postService);
        Posts post=getPost();
        post.setGroup(getGroup());
        //when
        when(postApiController.findById(POST_ID)).thenReturn(buildPostResponseDto(post));

        PostsResponseDto postsResponseDto=postApiController.findById(POST_ID);
        //then
        assertThat(postsResponseDto.getTitle()).isEqualTo("제목");
        assertThat(postsResponseDto.getContent()).isEqualTo("내용");
    }

    @Test
    public void 게시글_수정() throws Exception{
        //given
        Posts post=getPost();
        post.setTitle("바뀐제목");
        post.setContent("바뀐내용");
        given(postApiController.update(POST_ID,buildPostUpdateRequest())).willReturn(POST_ID);
        given(postService.findById(POST_ID)).willReturn(buildPostResponseDto(post));
        given(postRepository.findById(POST_ID)).willReturn(post);
        //when
        PostsResponseDto postsResponseDto=postApiController.findById(POST_ID);

        //then
        assertThat(postsResponseDto.getTitle()).isEqualTo("바뀐제목");
        assertThat(postsResponseDto.getContent()).isEqualTo("바뀐내용");
    }

    private PostsResponseDto buildPostResponseDto(Posts post){
        return new PostsResponseDto(post);
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
