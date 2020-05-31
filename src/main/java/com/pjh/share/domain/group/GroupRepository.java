package com.pjh.share.domain.group;

import com.pjh.share.domain.video.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group,Long> {
    @Query("select g from Group g where g.id!=any(select gc.group.id from GroupAccount gc where gc.account.id=:accountId)")
    List<Group> findAllGroup(@Param("accountId") Long id);
}
