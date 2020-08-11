package com.pjh.share.domain.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InviteAuthWaitRepository extends JpaRepository<InviteAuthWait,Long> {
    @Query("select i from InviteAuthWait i where i.receiver=:receiver")
    List<InviteAuthWait> findInviteAuthWaitList(@Param("receiver")String receiver);
}
