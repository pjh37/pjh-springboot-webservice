package com.pjh.share.domain.groupaccount;

import com.pjh.share.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupAccountRepository extends JpaRepository<GroupAccount,Long> {
    @Query("select g.group from GroupAccount g where g.account.id=:accountId order by g.id desc")
    List<Group> findAllMyGroup(@Param("accountId") Long id);
}
