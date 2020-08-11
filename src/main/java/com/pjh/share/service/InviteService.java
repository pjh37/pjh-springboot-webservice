package com.pjh.share.service;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.friend.*;
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
    private final FriendRepository friendRepository;

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
    public List<InviteAuthWaitDto> findInviteAuthWaitList(String receiver){
        return inviteAuthWaitRepository.findInviteAuthWaitList(receiver).stream()
                .map(InviteAuthWaitDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean inviteConfirm(Long id){
        InviteAuthWait inviteAuthWait=inviteAuthWaitRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("없는 요청입니다."));
        inviteAuthWait.confirmed();

        Account receiver=accountRepository.findByName(inviteAuthWait.getReceiver());
        Account sender=accountRepository.findByName(inviteAuthWait.getSender());

        Friend friend=Friend.builder()
                .accountId(receiver.getId())
                .friendId(sender.getId())
                .build();
        friendRepository.save(friend);
        return true;
    }

    @Transactional
    public boolean inviteRefuse(Long id){
        InviteAuthWait inviteAuthWait=inviteAuthWaitRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("없는 요청입니다."));
        inviteAuthWait.refused();
        return true;
    }
}
