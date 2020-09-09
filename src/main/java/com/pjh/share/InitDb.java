package com.pjh.share;

import com.pjh.share.domain.account.Account;
import com.pjh.share.domain.account.AccountRepository;
import com.pjh.share.domain.account.Role;
import com.pjh.share.domain.friend.Friend;
import com.pjh.share.domain.friend.FriendRepository;
import com.pjh.share.domain.group.Group;
import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.domain.groupaccount.GroupAccount;
import com.pjh.share.domain.groupaccount.GroupAccountRepository;
import com.pjh.share.domain.post.Posts;
import com.pjh.share.domain.post.PostsRepository;
import com.pjh.share.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import com.pjh.share.domain.groupaccount.*;
import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    @PostConstruct
    public void init(){
        //initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final AccountRepository accountRepository;
        private final GroupAccountRepository groupAccountRepository;
        private final GroupRepository groupRepository;
        private final PostsRepository postsRepository;
        private final FriendRepository friendRepository;
        private ArrayList<Account> users=new ArrayList<>();
        public void dbInit(){

            for(int i=0;i<10;i++){
                Account user=Account.builder()
                        .name("홍길동"+i)
                        .email(i+"jjjj1352@naver.com")
                        .password("123123123"+i)
                        .authString("toejt23j2p25453pwjer"+i)
                        .role(Role.USER).build();
                accountRepository.save(user);
                users.add(user);

            }


            friendRepository.save(Friend.builder()
                    .accountId(users.get(0).getId())
                    .friendId(users.get(1).getId())
                    .build());
            friendRepository.save(Friend.builder()
                    .accountId(users.get(0).getId())
                    .friendId(users.get(2).getId())
                    .build());
            friendRepository.save(Friend.builder()
                    .accountId(users.get(0).getId())
                    .friendId(users.get(3).getId())
                    .build());
            friendRepository.save(Friend.builder()
                    .accountId(users.get(3).getId())
                    .friendId(users.get(1).getId())
                    .build());
            friendRepository.save(Friend.builder()
                    .accountId(users.get(3).getId())
                    .friendId(users.get(7).getId())
                    .build());
            friendRepository.save(Friend.builder()
                    .accountId(users.get(3).getId())
                    .friendId(users.get(6).getId())
                    .build());


            Group group=Group.builder()
                    .title("스프링모임")
                    .description("고수만")
                    .currentNum(5)
                    .totalNum(10)
                    .password(null)
                    .build();
            group.setAccount(users.get(0));
            group=groupRepository.save(group);
            groupAccountRepository.save(new GroupAccount(users.get(0),group, com.pjh.share.domain.groupaccount.Role.ADMIN));

            for(int i=0;i<57;i++){
                Posts post=Posts.builder().name(users.get(0).getName())
                        .content("안녕하세요"+i)
                        .groupId(group.getId())
                        .title("제목입니다"+i)
                        .clickCount(0L)
                        .build();
                post.setGroup(group);
                postsRepository.save(post);
            }


        }
    }


}
