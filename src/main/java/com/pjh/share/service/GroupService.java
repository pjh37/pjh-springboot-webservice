package com.pjh.share.service;

import com.pjh.share.component.S3Uploader;
import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.account.SessionUser;
import com.pjh.share.domain.file.File;
import com.pjh.share.domain.file.FileRepository;
import com.pjh.share.domain.group.Group;
import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.domain.groupaccount.GroupAccount;
import com.pjh.share.domain.groupaccount.GroupAccountRepository;
import com.pjh.share.domain.groupaccount.Role;
import com.pjh.share.util.FileUtil;
import com.pjh.share.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GroupService {
    private static final String DIR_NAME="images";
    private final GroupAccountRepository groupAccountRepository;
    private final GroupRepository groupRepository;
    private final FileRepository fileRepository;
    private final AccountRepository accountRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long save(GroupCreateRequestDto dto,SessionUser user)throws Exception{
        Group group=groupRepository.save(dto.toEntity());
        Account account=accountRepository.findById(user.getId())
                .orElseThrow(()->new IllegalArgumentException("없는 회원입니다."));

        if(dto.getFile()!=null&&dto.getFile().getSize()!=0){
            group.thumbnailUpdate(s3Uploader.upload(dto.getFile(),DIR_NAME));
        }

        group.setAccount(account);
        group.memberJoin();
        groupAccountRepository.save(new GroupAccount(account,group, Role.ADMIN));
        return group.getId();
    }

    @Transactional
    public Long join(GroupJoinRequestDto requestDto, SessionUser user){
        Group group=groupRepository.findById(requestDto.getGroupId())
                .orElseThrow(()->new IllegalArgumentException("없는 그룹입니다."));
        group.memberJoin();

        Account account=accountRepository.findById(user.getId())
                .orElseThrow(()->new IllegalArgumentException("없는 회원입니다."));
        GroupAccount groupAccount= GroupAccount.builder()
                .account(account)
                .group(group)
                .role(Role.GUEST)
                .build();
        return groupAccountRepository.save(groupAccount).getId();
    }

    /*
    @Transactional(readOnly = true)
    public List<GroupListResponseDto> findAllGroup(Long id){
        return groupRepository.findAllGroup(id).stream().map(GroupListResponseDto::new)
                .collect(Collectors.toList());
    }
     */
    @Transactional(readOnly = true)
    public List<GroupListResponseDto> findAll(){
        return groupRepository.findAll().stream().map(GroupListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GroupResponseDto findById(Long id){
        Group entity=groupRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당그룹이 없습니다"));
        return new GroupResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<GroupListResponseDto> findAllMyGroup(Long id){
        return groupAccountRepository.findAllMyGroup(id)
                .stream()
                .map(GroupListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroupMemberListResponseDto> findGroupMemberByGroupId(Long groupId){
        return groupAccountRepository.findGroupMemberByGroupId(groupId)
                .stream()
                .map(GroupMemberListResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public boolean groupPwCheck(GroupPwCheckRequestDto requestDto){
        Group entity=groupRepository.findById(requestDto.getId())
                .orElseThrow(()->new IllegalArgumentException("해당그룹이 없습니다"));
        return entity.getPassword().equals(requestDto.getPassword());
    }

    @Transactional
    public boolean groupMemberCheck(Long groupId,SessionUser user){
        return groupAccountRepository.existsByAccountIdAndGroupId(user.getId(),groupId);
    }


}
