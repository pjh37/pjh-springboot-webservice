package com.pjh.share.repository;

import com.pjh.share.domain.comment.Comment;
import com.pjh.share.domain.comment.CommentRepository;
import com.pjh.share.domain.post.Posts;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Transactional
public class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @BeforeEach
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

        assertThat(comment.getName()).isEqualTo(name);
        assertThat(comment.getContent()).isEqualTo(content);
        assertThat(comment.getPosts().getTitle()).isEqualTo(postTitle);
        assertThat(comment.getPosts().getContent()).isEqualTo(postContent);
        assertThat(comment.getPosts().getName()).isEqualTo(postAuthor);
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
        assertThat(comment.getContent()).isEqualTo(content2);
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
        assertThat(commentRepository.findAll().size()).isEqualTo(1);
        commentRepository.delete(comment);
        assertThat(commentRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void 댓글작성_대댓글작성_대댓글불러오기(){
        Integer curPage=0;
        Integer pageSize=10;
        String name="홍길동";
        String content="댓글입니다. 안녕하세요";
        String replyName="둘리";
        String replyContent="대댓글입니다.";
        Comment commentParent=commentRepository.save(Comment.builder()
                .name(name)
                .content(content)
                .likeCount(0)
                .dislikeCount(0)
                .childCount(0)
                .build());

        for(int i=0;i<3;i++){
            Comment comment=Comment.builder()
                    .name(replyName)
                    .content(replyContent)
                    .likeCount(0)
                    .dislikeCount(0)
                    .childCount(0)
                    .build();
            comment.setParent(commentParent);
            commentRepository.save(comment);
        }
        Pageable pageable= PageRequest.of(curPage,pageSize,new Sort(Sort.Direction.DESC,"id"));
        List<Comment> commentList=commentRepository.findAllChildByIdDesc(pageable,commentParent.getId());

        assertThat(commentList.size()).isEqualTo(3);
        assertThat(commentList.get(0).getName()).isEqualTo(replyName);
        assertThat(commentList.get(0).getContent()).isEqualTo(replyContent);
        assertThat(commentList.get(0).getParent().getName()).isEqualTo(name);
    }
}
