package com.pjh.share.domain.comment;

import com.pjh.share.web.dto.CommentListResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("select c from Comment c where c.posts.id=:postId and c.parent=-1 order by c.id desc")
    List<Comment> findAllDesc(Pageable page,@Param("postId") Long id);

    @Query("select c from Comment c where c.parent=:parentId order by c.id desc")
    List<Comment> findAllChildByIdDesc(Pageable page,@Param("parentId") Long id);
}
