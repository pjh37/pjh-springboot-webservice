package com.pjh.share.domain.groupaccount;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.group.Group;
import com.pjh.share.web.dto.GroupMemberListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupAccountRepository extends JpaRepository<GroupAccount,Long> {
    @Query("select g.group from GroupAccount g where g.account.id=:accountId order by g.id desc")
    List<Group> findAllMyGroup(@Param("accountId") Long id);

    @Query("select g from GroupAccount g where g.group.id=:groupId")
    List<GroupAccount> findGroupMemberByGroupId(@Param("groupId") Long groupId);

    boolean existsByAccountIdAndGroupId(Long accountId, Long groupId);

    @Modifying
    @Query("delete from GroupAccount g where g.group.id=:groupId and g.account.id=:accountId")
    void withdrawGroup(@Param("accountId") Long accountId, @Param("groupId") Long groupId);
    /*
    @Query("select g.group from GroupAccount g where g.account.id=:accountId and g.group.id=:groupId")
    boolean existsByAccountIdAndGroupId(@Param("accountId") Long accountId,@Param("groupId") Long groupId);

     */


}
