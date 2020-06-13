package com.pjh.share.domain.group;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupRepositoryTest {
    @Autowired
    GroupRepository groupRepository;

    @After
    public void cleanUp(){
        groupRepository.deleteAll();
    }

    @Test
    public void 그룹생성_불러오기(){

    }

    @Test
    public void 그룹생성_수정하기(){

    }

    @Test
    public void 그룹생성_삭제하기(){

    }

    @Test
    public void 그룹생성_게시글작성(){

    }

    @Test
    public void 그룹별_게시글불러오기(){

    }

    @Test
    public void 그룹생성_게시글들작성_게시글들불러오기(){

    }

    @Test
    public void 그룹생성_게시글들작성_그룹삭제(){

    }

    @Test
    public void 그룹생성_게시글작성_댓글작성(){

    }

    @Test
    public void 그룹생성_게시글작성_댓글작성_게시글삭제(){

    }

    @Test
    public void 그룹생성_게시글작성_댓글작성_댓글수정(){

    }

    @Test
    public void 그룹생성_게시글작성_댓글작성_댓글삭제(){

    }



}
