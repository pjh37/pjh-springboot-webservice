package com.pjh.share.service;

import com.pjh.share.domain.post.Posts;
import com.pjh.share.web.dto.PostsUpdateRequestDto;

import java.util.List;

public interface PostRepository {
    Posts findById(Long id);

    Posts save(Posts post);

    Long update(Long id, PostsUpdateRequestDto requestDto);

    void delete(Long id);

    List<Posts> findAllDesc();

    Long postClicked(Long id);
}
