package com.pjh.share.service;

import com.pjh.share.domain.file.FileRepository;
import com.pjh.share.web.dto.GroupCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class FileService {
    private FileRepository fileRepository;


}
