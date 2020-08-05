package com.pjh.share.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByEmail(String email);
    Account findByAuthString(String authString);
    Account findByName(String name);
    boolean existsByName(String name);

    //@Query("select a from Account a where a.name like ")

    List<Account> findByNameContaining(String name);
}
