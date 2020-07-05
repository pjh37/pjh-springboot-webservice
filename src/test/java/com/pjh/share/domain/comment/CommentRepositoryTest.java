package com.pjh.share.domain.comment;

import com.pjh.share.domain.post.Posts;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @After
    public void cleanUp(){
        commentRepository.deleteAll();
    }

    @Test
    public void 댓글작성_불러오기(){
        String postTitle="게시글 제목입니다.";
        String postAuthor="둘리";
        String postContent="호이호이";
        String name="홍길동";
        String content="댓글입니다. 안녕하세요";
        Posts post=Posts.builder()
                .title(postTitle)
                .content(postContent)
                .name(postAuthor)
                .groupId(0L)
                .build();

        Comment comment=commentRepository.save(Comment.builder()
                .name(name)
                .content(content)
                .likeCount(0)
                .dislikeCount(0)
                .childCount(0)
                .build());

        comment.setPosts(post);

        assertEquals(comment.getName(),name);
        assertEquals(comment.getContent(),content);
        assertEquals(comment.getPosts().getTitle(),postTitle);
        assertEquals(comment.getPosts().getContent(),postContent);
        assertEquals(comment.getPosts().getName(),postAuthor);
    }

    @Test
    public void 댓글작성_수정하기(){
        String name="홍길동";
        String content="댓글입니다. 안녕하세요";
        Comment comment=commentRepository.save(Comment.builder()
                .name(name)
                .content(content)
                .likeCount(0)
                .dislikeCount(0)
                .childCount(0)
                .build());
        String content2="수공하세요";
        comment.update(content2);
        assertEquals(comment.getContent(),content2);
    }

    @Test
    public void 댓글작성_삭제하기(){
        String name="홍길동";
        String content="댓글입니다. 안녕하세요";
        Comment comment=commentRepository.save(Comment.builder()
                .name(name)
                .content(content)
                .likeCount(0)
                .dislikeCount(0)
                .childCount(0)
                .build());
        assertEquals(1,commentRepository.findAll().size());
        commentRepository.delete(comment);
        assertEquals(0,commentRepository.findAll().size());
    }

    @Test
    public void 댓글작성_대댓글작성_대댓글불러오기(){
        Integer curPage=0;
        Integer pageSize=10;
        String name="홍길동";
        String content="댓글입니다. 안녕하세요";
        String replyName="둘리";
        String replyContent="대댓글입니다.";
        //댓글10개 생성
        for(int i=0;i<10;i++){
            commentRepository.save(Comment.builder()
                    .name(name)
                    .content(content)
                    .likeCount(0)
                    .dislikeCount(0)
                    .childCount(0)
                    .build());
        }
        for(int i=0;i<3;i++){
            commentRepository.save(Comment.builder()
                    .name(replyName)
                    .content(replyContent)
                    .likeCount(0)
                    .dislikeCount(0)
                    .childCount(0)
                    .build());
        }
        Pageable pageable= PageRequest.of(curPage,pageSize,new Sort(Sort.Direction.DESC,"id"));
        List<Comment> commentList=commentRepository.findAllChildByIdDesc(pageable,2L);
        Comment comment=commentList.get(0);
        assertEquals(3,commentList.size());
        assertEquals(comment.getName(),replyName);
        assertEquals(comment.getContent(),replyContent);
    }
}
