package com.pjh.share.domain.account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,length = 20 ,nullable = false,columnDefinition = "varchar(255) default '익명'")
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(length = 500 ,nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Account(String name,String email,String password,Role role){
        this.name=name;
        this.email=email;
        this.password=password;
        this.role=role;
    }
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password=passwordEncoder.encode(this.password);
    }

}
