package com.pjh.share.domain.chatroomaccount;

import com.pjh.share.domain.chatroom.ChatRoom;
import com.pjh.share.domain.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomAccountRepository extends JpaRepository<ChatRoomAccount,Long> {
    @Query("select c.chatRoom from ChatRoomAccount c where c.account.id=:accountId order by g.id desc")
    List<ChatRoom> findAllMyChatRoom(@Param("accountId") Long id);
}
