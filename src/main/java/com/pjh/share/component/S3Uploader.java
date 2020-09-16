package com.pjh.share.component;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class S3Uploader {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile,String dirName)throws Exception{
        File uploadFile=convert(multipartFile)
                .orElseThrow(()->new IllegalArgumentException("MultipartFile -> File로 전환 실패했습니다."));
        return upload(uploadFile,dirName);
    }

    private String upload(File uploadFile,String dirName){
        String fileName=dirName+"/"+uploadFile.getName();
        String uploadUrl=putS3(uploadFile,fileName);
        logger.info("uploadedUrl in S3Uploader component : "+uploadUrl);
        removeNewFile(uploadFile);
        return uploadUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket,fileName,uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket,fileName).toString();
    }

    private void removeNewFile(File targetFile){
        if(targetFile.delete()){
            logger.info("파일이 삭제되었습니다.");
        }else{
            logger.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file)throws IOException {
        File convertFile=new File(file.getOriginalFilename());
        if(convertFile.createNewFile()){
            try(FileOutputStream fos=new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}