package com.pjh.share.domain.groupaccount;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GROUP_GUEST","임시회원"),
    USER("ROLE_GROUP_USER","정회원"),
    ADMIN("ROLE_GROUP_ADMIN","관리자");


    private final String key;
    private final String title;
}
