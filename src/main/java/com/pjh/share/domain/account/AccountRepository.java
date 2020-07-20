package com.pjh.share.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByEmail(String email);
    Account findByAuthString(String authString);
    Account findByName(String name);
    boolean existsByName(String name);
}
