package com.pjh.share.web.dto;

import com.pjh.share.domain.video.Video;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
public class VideoUploadRequestDto {
    private Long groupId;
    private String title;
    private MultipartFile file;

    public Video toEntity(){
        String fileName= UUID.randomUUID().toString();
        return Video.builder().title(title)
                .fileName(fileName)
                .fileOriginalName(file.getOriginalFilename())
                .build();
    }
}
