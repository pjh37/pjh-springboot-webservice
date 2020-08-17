package com.pjh.share.repository;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.account.Role;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.isEquals;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;


@DataJpaTest
@Transactional
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    public void cleanUp(){
        accountRepository.deleteAll();
    }

    @Test
    public void 회원저장_불러오기(){
        String name="홍길동";
        String email="jjjj1352@naver.com";
        String password="123123123";
        String authString= UUID.randomUUID().toString();

        accountRepository.save(Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .authString(authString)
                .role(Role.USER).build());
        List<Account> accountList=accountRepository.findAll();
        Account account=accountList.get(0);

        assertThat(account.getName()).isEqualTo(name);
        assertThat(account.getEmail()).isEqualTo(email);
    }

    @Test
    public void 회원저장_업데이트(){
        String name="홍길동";
        String email="jjjj1352@naver.com";
        String password="123123123";
        String authString= UUID.randomUUID().toString();
        accountRepository.save(Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .authString(authString)
                .role(Role.USER).build());
        List<Account> accountList=accountRepository.findAll();
        String name2="둘리";
        String password2="987987987";

        Account account=accountList.get(0);
        account.setName(name2);
        account.setPassword(password2);

        assertThat(account.getName()).isEqualTo(name2);
        assertThat(account.getPassword()).isEqualTo(password2);
    }
    @Test
    public void 회원저장_삭제(){
        String name="홍길동";
        String email="jjjj1352@naver.com";
        String password="123123123";
        String authString= UUID.randomUUID().toString();
        Account account=accountRepository.save(Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .authString(authString)
                .role(Role.USER).build());
        Long id=account.getId();
        accountRepository.delete(account);
        List<Account> accountList=accountRepository.findAll();
        assertThat(accountList.size()).isEqualTo(0);
    }
}
