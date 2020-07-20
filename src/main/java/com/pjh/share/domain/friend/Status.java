package com.pjh.share.domain.friend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    REFUSE("STATUS_REFUSE","초대거절"),
    WAIT("STATUS_WAIT","응답대기"),
    INVITING("STATUS_INVITING","초대요청");


    private final String key;
    private final String title;
}
