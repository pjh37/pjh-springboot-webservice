package com.pjh.share.service;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.account.Role;
import com.pjh.share.domain.account.UserAccount;
import com.pjh.share.web.dto.AccountCreateRequestDto;
import com.pjh.share.web.dto.AccountSearchResponseDto;
import com.pjh.share.domain.account.SessionUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.web.socket.events.SessionConnectEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountService implements UserDetailsService {
    private Logger logger= LoggerFactory.getLogger(getClass());
    private final int SEARCH_ACCOUNT_LIMIT=10;
    private final AccountRepository accountRepository;
    private final HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account=accountRepository.findByEmail(email);
        if(account==null){
            throw  new UsernameNotFoundException(email);
        }
        SessionUser sessionUser= SessionUser.builder()
                .id(account.getId())
                .name(account.getName())
                .email(account.getEmail())
                .role(account.getRole())
                .build();

        httpSession.setAttribute("user",sessionUser);
        logger.info("======================");
        logger.info("UserDetails 동작");
        logger.info("======================");
        return new UserAccount(account);
    }

    @Transactional
    public Long save(AccountCreateRequestDto requestDto){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        //requestDto.setRole(Role.USER);
        return accountRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public String findByAuth(String authString){
        Account account=accountRepository.findByAuthString(authString);
        return account==null ? null : account.getEmail();
    }

    @Transactional(readOnly = true)
    public List<AccountSearchResponseDto> findByNameContaining(String name){
        return accountRepository.findByNameContaining(name).stream()
                .limit(SEARCH_ACCOUNT_LIMIT)
                .map(AccountSearchResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void setRole(String email,Role role){
        Account account=accountRepository.findByEmail(email);
        account.setRole(role);
    }
}
