package com.pjh.share.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RedisMemberDto implements Serializable {
    private static final long serialVersionUID=1L;
    private String name;
    private Integer age;
}
