package com.pjh.share.service;

import com.pjh.share.domain.post.Posts;
import com.pjh.share.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostUnitRepository implements PostRepository{
    private Long increment=0L;
    private Map<Long,Posts> postsMap=new HashMap<>();

    @Override
    public Posts findById(Long id) {
        return postsMap.get(id);
    }

    @Override
    public Posts save(Posts post) {
        post.setId(increment++);
        postsMap.put(post.getId(),post);
        return post;
    }

    @Override
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts post=postsMap.get(id);
        post.setTitle(requestDto.getTitle());
        post.setContent(requestDto.getContent());
        postsMap.put(post.getId(),post);
        return null;
    }

    @Override
    public void delete(Long id) {
        postsMap.remove(id);
    }

    @Override
    public List<Posts> findAllDesc() {
        return null;
    }

    @Override
    public Long postClicked(Long id) {
        return null;
    }
}
