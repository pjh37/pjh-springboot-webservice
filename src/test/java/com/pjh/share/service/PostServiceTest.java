package com.pjh.share.service;

import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.web.dto.PostsSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    private static final Long USER_ID=1L;

    @Mock
    private PostsRepository postRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private AccountRepository accountRepository;

    private SessionUser sessionUser;

    private PostService postService;

    @BeforeEach
    public void setup(){
        postService=new PostService(postRepository,groupRepository,accountRepository);
        sessionUser= SessionUser.builder()
                .name("user")
                .id(USER_ID)
                .build();
    }
    @Test
    void save() {
        //given
        PostsSaveRequestDto postsSaveRequestDto=buildPostsSaveRequestDto();
        //given(postRepository.save())
        postService.save(postsSaveRequestDto,sessionUser);
    }

    @Test
    void findAllDesc() {
    }

    @Test
    void getPostCount() {
    }

    @Test
    void getPageList() {
    }

    @Test
    void postClicked() {


    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    PostsSaveRequestDto buildPostsSaveRequestDto(){
        PostsSaveRequestDto postsSaveRequestDto=new PostsSaveRequestDto();
        postsSaveRequestDto.setName("홍길동");
        postsSaveRequestDto.setContent("내용");
        postsSaveRequestDto.setTitle("제목");
        postsSaveRequestDto.setGroupId(1L);
        return  postsSaveRequestDto;
    }
}