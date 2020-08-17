package com.pjh.share.service;

import com.pjh.share.domain.post.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Posts findByName(String name){
        return postRepository.findByName(name);
    }

    public void save(Posts post){
        postRepository.save(post);
    }
}
