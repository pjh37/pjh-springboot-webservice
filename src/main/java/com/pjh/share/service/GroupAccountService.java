package com.pjh.share.service;

import com.pjh.share.domain.groupaccount.GroupAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupAccountService {
    private final GroupAccountRepository groupAccountRepository;

    @Transactional
    public void withdrawGroup(Long accountId,Long groupId){
        groupAccountRepository.withdrawGroup(accountId,groupId);
    }
}
