package com.pjh.share.service;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.friend.InviteAuthWait;
import com.pjh.share.domain.friend.InviteAuthWaitRepository;
import com.pjh.share.domain.friend.State;
import com.pjh.share.web.dto.InviteAuthWaitDto;
import com.pjh.share.web.dto.InviteRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteAuthWaitRepository inviteAuthWaitRepository;
    private final AccountRepository accountRepository;
    @Transactional
    public boolean inviteRequest(Account sender, InviteRequestDto req){
        if(!accountRepository.existsByName(req.getName()))return false;
        Account receiver=accountRepository.findByName(req.getName());

        InviteAuthWait inviteAuthWait = InviteAuthWait.builder()
                .sender(sender)
                .receiver(receiver)
                .state(State.WAIT)
                .build();

        inviteAuthWaitRepository.save(inviteAuthWait);
        return true;
    }

    @Transactional(readOnly = true)
    public List<InviteAuthWaitDto> findInviteAuthWaitList(String name){
        return inviteAuthWaitRepository.findInviteAuthWaitList(name).stream()
                .map(InviteAuthWaitDto::new)
                .collect(Collectors.toList());
    }
}
