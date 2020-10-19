package com.pjh.share.service;

import com.pjh.share.component.S3Uploader;
import com.pjh.share.domain.group.Group;
import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.domain.video.Video;
import com.pjh.share.domain.video.VideoRepository;
import com.pjh.share.util.FileUtil;
import com.pjh.share.web.dto.VideoListResponseDto;
import com.pjh.share.web.dto.VideoResponseDto;
import com.pjh.share.web.dto.VideoUploadRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {
    private Logger logger= LoggerFactory.getLogger(getClass());
    private static final String DIRECTORY_NAME="video";
    private final VideoRepository videoRepository;
    private final GroupRepository groupRepository;
    private final S3Uploader s3Uploader;
    @Transactional
    public Long save(VideoUploadRequestDto requestDto,String name)throws Exception {
        Group group=groupRepository.findById(requestDto.getGroupId()).orElseThrow(()->new IllegalArgumentException("없는 그룹입니다."));
        String uploadedUrl=s3Uploader.upload(requestDto.getFile(),DIRECTORY_NAME);
        Video video=videoRepository.save(requestDto.toEntity());
        video.setGroup(group);
        video.setName(name);
        video.setUrl(uploadedUrl);
        video.clickCountUpdate();
        return video.getId();
    }

    @Transactional
    public VideoResponseDto findById(Long id){
        Video video=videoRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 동영상이 없습니다"));
        return new VideoResponseDto(video);
    }


    @Transactional
    public List<VideoListResponseDto> findAllDesc(Long groupId){
        return videoRepository.findAllDesc(groupId)
                .stream()
                .map(VideoListResponseDto::new)
                .collect(Collectors.toList());
    }
}
