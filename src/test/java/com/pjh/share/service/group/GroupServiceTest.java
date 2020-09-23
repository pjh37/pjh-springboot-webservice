package com.pjh.share.service.group;

import com.pjh.share.component.S3Uploader;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;

import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.file.FileRepository;
import com.pjh.share.domain.group.Group;
import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.domain.groupaccount.GroupAccount;
import com.pjh.share.domain.groupaccount.GroupAccountRepository;
import com.pjh.share.domain.groupaccount.Role;
import com.pjh.share.service.GroupService;
import com.pjh.share.web.dto.GroupCreateRequestDto;
import com.pjh.share.web.dto.GroupPwCheckRequestDto;
import com.pjh.share.web.dto.GroupResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {
    private static final Long GROUP_ID=1L;
    private static final Long USER_ID=1L;
    @Mock
    private GroupAccountRepository groupAccountRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private S3Uploader s3Uploader;

    @Mock
    private AccountRepository accountRepository;

    private SessionUser sessionUser;

    private GroupService groupService;
    @BeforeEach
    public void setup(){
        groupService=new GroupService(groupAccountRepository,groupRepository
                ,fileRepository,accountRepository,s3Uploader);
        sessionUser=SessionUser.builder()
                .name("user")
                .id(USER_ID)
                .build();
    }

    @Test
    @DisplayName("그룹 생성 테스트")
    public void groupCreate() throws Exception{
        //given
        Group group=toGroupEntity();
        Account account=toAccountEntity();
        group.setAccount(account);
        GroupCreateRequestDto groupCreateRequestDto=buildGroupDto(group);
        GroupResponseDto groupResponseDto=buildGroupResponseDto(group);
        given(groupRepository.save(any())).willReturn(group);
        given(accountRepository.findById(USER_ID)).willReturn(Optional.of(account));
        given(groupRepository.findById(GROUP_ID)).willReturn(Optional.of(group));

        //when
        groupService.save(groupCreateRequestDto,sessionUser);

        //then
        GroupResponseDto groupResponseDto2=groupService.findById(GROUP_ID);
        assertThat(groupResponseDto2.getTitle()).isEqualTo("그룹이름");
    }
    @Test
    @DisplayName("그룹 멤버 여부 체크")
    public void groupMemberCheck()throws Exception{
        //given
        given(groupAccountRepository.existsByAccountIdAndGroupId(USER_ID,GROUP_ID)).willReturn(true);

        //when
        boolean result=groupService.groupMemberCheck(GROUP_ID,sessionUser);

        //then
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("그룹 비밀번호 맞을때 체크")
    public void groupPasswordCheckSuccess()throws Exception{
        //given
        Group group=toGroupEntity();
        given(groupRepository.findById(GROUP_ID)).willReturn(Optional.of(group));

        //when
        boolean result=groupService.groupPwCheck(buildGroupPwCheckRequestDto(GROUP_ID,"123"));

        //then
        assertThat(result).isEqualTo(true);
    }
    @Test
    @DisplayName("그룹 비밀번호 틀렸을때 체크")
    public void groupPasswordCheckFail()throws Exception{
        //given
        Group group=toGroupEntity();
        given(groupRepository.findById(GROUP_ID)).willReturn(Optional.of(group));

        //when
        boolean result=groupService.groupPwCheck(buildGroupPwCheckRequestDto(GROUP_ID,"123456"));

        //then
        assertThat(result).isEqualTo(false);
    }

    private GroupCreateRequestDto buildGroupDto(Group group){
        GroupCreateRequestDto dto=new GroupCreateRequestDto();
        dto.setTitle(group.getTitle());
        dto.setDescription(group.getDescription());
        dto.setTotalNum(group.getTotalNum());
        dto.setCurrentNum(group.getCurrentNum());
        dto.setPassword(group.getPassword());
        return dto;
    }
    private GroupPwCheckRequestDto buildGroupPwCheckRequestDto(Long groupId, String password){
        GroupPwCheckRequestDto groupPwCheckRequestTestDto =new GroupPwCheckRequestDto();
        groupPwCheckRequestTestDto.setId(groupId);
        groupPwCheckRequestTestDto.setPassword(password);
        return groupPwCheckRequestTestDto;
    }
    private Account toAccountEntity(){
        Account account=Account.builder()
                .name("유저")
                .email("abc@naver.com")
                .password("123123123")
                .authString("1234556")
                .build();
        return account;
    }
    private Group toGroupEntity(){
        Group group=Group.builder()
                .title("그룹이름")
                .description("그룹설명")
                .totalNum(20)
                .currentNum(0)
                .password("123")
                .build();
        group.setId(GROUP_ID);
        return group;
    }

    private GroupResponseDto buildGroupResponseDto(Group group){
        GroupResponseDto groupResponseDto=new GroupResponseDto(group);
        return groupResponseDto;
    }

}
