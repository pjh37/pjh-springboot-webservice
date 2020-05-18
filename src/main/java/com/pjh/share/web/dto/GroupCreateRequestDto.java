package com.pjh.share.web.dto;

import com.pjh.share.domain.file.File;
import com.pjh.share.domain.group.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
public class GroupCreateRequestDto{
    private String title;
    private String description;
    private Integer currentNum;
    private Integer totalNum;
    private String password;
    private MultipartFile file;

    public Group toEntity(){
        return Group.builder()
                .title(title)
                .description(description)
                .currentNum(currentNum)
                .totalNum(totalNum)
                .password(password)
                .build();
    }
    public File toFileEntity(){
        String fileName= UUID.randomUUID().toString();
        return File.builder()
                .fileName(fileName)
                .fileOriginalName(file.getOriginalFilename())
                .build();
    }
}
