package com.pjh.share.service;

import com.pjh.share.domain.file.File;
import com.pjh.share.domain.file.FileRepository;
import com.pjh.share.domain.group.Group;
import com.pjh.share.domain.group.GroupRepository;
import com.pjh.share.util.FileUtil;
import com.pjh.share.web.dto.GroupCreateRequestDto;
import com.pjh.share.web.dto.GroupListResponseDto;
import com.pjh.share.web.dto.GroupPwCheckRequestDto;
import com.pjh.share.web.dto.GroupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final FileRepository fileRepository;
    @Transactional
    public Long save(GroupCreateRequestDto dto)throws Exception{
        Group group=groupRepository.save(dto.toEntity());
        File file=dto.toFileEntity();
        group.thumbnailUpdate(file.getFileName());
        fileRepository.save(file).groupIdUpdate(group.getId());
        FileUtil.upload(dto.getFile(),file.getFileName());
        return group.getId();
    }

    @Transactional
    public List<GroupListResponseDto> findAll(){
        return groupRepository.findAll().stream().map(GroupListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public GroupResponseDto findById(Long id){
        Group entity=groupRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당그룹이 없습니다"));
        return new GroupResponseDto(entity);
    }

    @Transactional
    public boolean groupPwCheck(GroupPwCheckRequestDto requestDto){
        Group entity=groupRepository.findById(requestDto.getId())
                .orElseThrow(()->new IllegalArgumentException("해당그룹이 없습니다"));
        return entity.getPassword().equals(requestDto.getPassword());
    }
}
