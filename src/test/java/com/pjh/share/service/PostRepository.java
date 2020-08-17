package com.pjh.share.service;

import com.pjh.share.domain.post.Posts;

public interface PostRepository {
    Posts findByName(String name);
    void save(Posts post);
}
