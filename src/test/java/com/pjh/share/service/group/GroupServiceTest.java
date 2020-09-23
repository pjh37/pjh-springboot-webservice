package com.pjh.share.service.group;

import com.pjh.share.component.S3Uploader;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.account.Role;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.file.FileRepository;
import com.pjh.share.domain.group.Group;
import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.domain.groupaccount.GroupAccountRepository;
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

    @BeforeEach
    public void setup(){


        sessionUser=SessionUser.builder()
                .name("user")
                .role(Role.USER)
                .id(USER_ID)
                .build();
    }

    @Test
    @DisplayName("그룹 생성 테스트")
    public void groupCreate() throws Exception{
        //given
        GroupService groupService=new GroupService(groupAccountRepository,groupRepository
                ,fileRepository,accountRepository,s3Uploader);
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

    /*
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

     */
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
                .role(Role.USER).build();
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
