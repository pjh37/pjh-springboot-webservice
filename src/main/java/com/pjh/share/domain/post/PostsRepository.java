package com.pjh.share.domain.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    @Query("select p from Posts p where p.group.id=:groupId")
    List<Posts> findAllDesc(Pageable request,@Param("groupId") Long groupId);

    @Query("select count(p) from Posts p where p.group.id=:groupId")
    Long findGroupPostCount(@Param("groupId") Long groupId);
}
