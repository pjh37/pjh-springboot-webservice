package com.pjh.share.controller;

import com.pjh.share.component.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class TestController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private final S3Uploader s3Uploader;

    @GetMapping("/uploadTest")
    public String index(){
        return "uploadTest";
    }

    @PostMapping("/test/upload")
    @ResponseBody
    public String upload(@RequestParam("data")MultipartFile multipartFile)throws Exception{
        String uploadUrl=s3Uploader.upload(multipartFile,"static");
        logger.info("uploadUrl : "+uploadUrl);
        return uploadUrl;
    }
}
