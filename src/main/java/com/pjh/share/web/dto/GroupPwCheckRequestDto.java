package com.pjh.share.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupPwCheckRequestDto {
    private Long id;
    private String password;
}
