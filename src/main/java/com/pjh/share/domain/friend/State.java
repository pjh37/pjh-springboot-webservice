package com.pjh.share.domain.friend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public enum State {
    REFUSE("REFUSE","초대거절"),
    WAIT("WAIT","응답대기"),
    CONFIRM("CONFIRM","초대수락");


    private final String key;
    private final String value;
}
