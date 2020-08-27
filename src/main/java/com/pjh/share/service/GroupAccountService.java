package com.pjh.share.service;

import com.pjh.share.domain.group.Group;
import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.domain.groupaccount.GroupAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupAccountService {
    private final GroupAccountRepository groupAccountRepository;
    private final GroupRepository groupRepository;
    @Transactional
    public void withdrawGroup(Long accountId,Long groupId){
        Group group=groupRepository.findById(groupId).orElseThrow(()->new IllegalArgumentException("없는 그룹"));
        group.memberWithdraw();
        groupAccountRepository.withdrawGroup(accountId,groupId);
    }
}
