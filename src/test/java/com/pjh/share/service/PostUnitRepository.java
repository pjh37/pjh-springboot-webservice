package com.pjh.share.service;

import com.pjh.share.domain.post.Posts;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PostUnitRepository implements PostRepository{
    private Map<String,Posts> postsMap=new HashMap<>();

    @Override
    public Posts findByName(String name) {
        return postsMap.get(name);
    }

    @Override
    public void save(Posts post) {
        postsMap.put(post.getName(),post);
    }
}
