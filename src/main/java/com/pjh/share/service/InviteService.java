package com.pjh.share.service;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.friend.FriendRepository;
import com.pjh.share.domain.friend.InviteResWait;
import com.pjh.share.domain.friend.InviteResWaitRepository;
import com.pjh.share.domain.friend.Status;
import com.pjh.share.web.dto.InviteRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteResWaitRepository inviteResWaitRepository;
    private final AccountRepository accountRepository;
    @Transactional
    public boolean inviteRequest(Account sender, InviteRequestDto req){
        if(!accountRepository.existsByName(req.getName()))return false;
        Account receiver=accountRepository.findByName(req.getName());

        InviteResWait inviteResWait=InviteResWait.builder()
                .senderId(sender)
                .receiverId(receiver)
                .status(Status.INVITING)
                .build();

        inviteResWaitRepository.save(inviteResWait);
        return true;
    }
}
