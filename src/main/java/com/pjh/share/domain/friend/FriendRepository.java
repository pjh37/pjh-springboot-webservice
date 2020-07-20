package com.pjh.share.domain.friend;

import com.pjh.share.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend,Long> {
    /*
    @Query("select f from friends f where f.accountId=:accountId and f.friendId=:friendId")
    List<Friend> findAllFriend(@Param("accountId") Long accountId, @Param("friendId") Long friendId);

     */
}
