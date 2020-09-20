package com.pjh.share.service.group;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.account.Role;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.service.GroupService;
import com.pjh.share.web.dto.GroupCreateRequestDto;
import com.pjh.share.web.dto.GroupPwCheckRequestDto;
import com.pjh.share.web.dto.GroupResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class GroupServiceTest {
    @Autowired
    private GroupService groupService;

    @Autowired
    private AccountRepository accountRepository;

    private SessionUser sessionUser;

    @BeforeEach
    public void setUp(){
        Long accountId=accountRepository.save(Account.builder().name("유저").password("123")
                .email("abc").role(Role.USER).authString("abc").build()).getId();
        sessionUser=SessionUser.builder()
                .name("유저")
                .role(Role.USER)
                .id(accountId)
                .build();
    }

    @Test
    @DisplayName("그룹 생성 테스트")
    public void groupCreate() throws Exception{
        Long savedId=groupService.save(buildGroupDto(),sessionUser);
        GroupResponseDto dto=groupService.findById(savedId);

        assertThat(dto.getTitle()).isEqualTo("그룹이름");
        assertThat(dto.getDescription()).isEqualTo("그룹설명");
    }

    @Test
    @DisplayName("그룹 멤버 여부 체크")
    public void groupMemberCheck()throws Exception{
        Long accountId=accountRepository.save(Account.builder().name("유저2").password("12345")
                .email("abcdef").role(Role.USER).authString("abcdf").build()).getId();
        Long savedId=groupService.save(buildGroupDto(),sessionUser);
        SessionUser newUser=SessionUser.builder()
                .name("유저2")
                .role(Role.USER)
                .id(accountId)
                .build();
        boolean groupCreatorCheckResult=groupService.groupMemberCheck(savedId,sessionUser);
        boolean newUserCheckResult=groupService.groupMemberCheck(savedId,newUser);

        assertThat(groupCreatorCheckResult).isEqualTo(true);
        assertThat(newUserCheckResult).isEqualTo(false);
    }


    @Test
    @DisplayName("그룹 비밀번호 맞을때 체크")
    public void groupPasswordCheckSuccess()throws Exception{
        Long savedId=groupService.save(buildGroupDto(),sessionUser);
        boolean result=groupService.groupPwCheck(buildGroupPwCheckRequestDto(savedId,"123"));

        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("그룹 비밀번호 틀렸을때 체크")
    public void groupPasswordCheckFail()throws Exception{
        Long savedId=groupService.save(buildGroupDto(),sessionUser);
        boolean result=groupService.groupPwCheck(buildGroupPwCheckRequestDto(savedId,"123456"));

        assertThat(result).isEqualTo(false);
    }
    private GroupCreateRequestDto buildGroupDto(){
        GroupCreateRequestDto dto=new GroupCreateRequestDto();
        dto.setTitle("그룹이름");
        dto.setDescription("그룹설명");
        dto.setTotalNum(20);
        dto.setCurrentNum(0);
        dto.setPassword("123");
        return dto;
    }
    private GroupPwCheckRequestDto buildGroupPwCheckRequestDto(Long groupId, String password){
        GroupPwCheckRequestDto groupPwCheckRequestTestDto =new GroupPwCheckRequestDto();
        groupPwCheckRequestTestDto.setId(groupId);
        groupPwCheckRequestTestDto.setPassword(password);
        return groupPwCheckRequestTestDto;
    }


}
