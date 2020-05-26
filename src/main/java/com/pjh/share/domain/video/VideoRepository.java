package com.pjh.share.domain.video;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video,Long> {
    @Query("select v from Video v where v.group.id=:groupId order by v.id desc")
    List<Video> findAllDesc(@Param("groupId") Long id);
}
