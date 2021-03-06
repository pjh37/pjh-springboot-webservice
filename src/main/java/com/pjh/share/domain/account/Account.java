package com.pjh.share.domain.account;

import com.pjh.share.domain.group.Group;
import com.pjh.share.domain.groupaccount.GroupAccount;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,length = 20 ,nullable = false,columnDefinition = "varchar(255) default '익명'")
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(length = 500 ,nullable = false)
    private String password;

    @Column(length = 500)
    private String authString;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    //자신이 가입한 그룹
    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupAccount> groupAccounts=new ArrayList<>();

    //자신이 만든 그룹
    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group> groups=new ArrayList<>();

    @Builder
    public Account(String name,String email,String password,String authString,Role role){
        this.name=name;
        this.email=email;
        this.password=password;
        this.authString=authString;
        this.role=role;
    }
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password=passwordEncoder.encode(this.password);
    }

}
