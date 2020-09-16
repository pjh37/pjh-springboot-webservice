package com.pjh.share.service.post;

import com.pjh.share.domain.post.Posts;
import com.pjh.share.web.dto.PostsListResponseDto;
import com.pjh.share.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final static Integer PAGE_SIZE=10;
    private final static Integer BLOCK_PAGE_SIZE=4;
    private final PostRepository postRepository;

    public Posts findById(Long id){
        return postRepository.findById(id);
    }

    public Long save(Posts post){
        return postRepository.save(post).getId();
    }

    public List<PostsListResponseDto> findAllDesc(Integer curPage,Long groupId){
        Pageable pageable= PageRequest.of(curPage-1,PAGE_SIZE,Sort.by("id").descending());
        return null;
    }

    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        return postRepository.update(id,requestDto);
    }

    public void delete(Long id){
        postRepository.delete(id);
    }
}
