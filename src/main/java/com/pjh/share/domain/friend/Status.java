package com.pjh.share.domain.friend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    REFUSE("REFUSE","초대거절"),
    WAIT("WAIT","응답대기"),
    INVITING("INVITING","초대요청");


    private final String key;
    private final String title;
}
