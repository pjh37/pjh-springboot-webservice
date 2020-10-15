package com.pjh.share.controller;

import com.pjh.share.component.S3Uploader;
import com.pjh.share.web.dto.RedisMemberDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class TestController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private final S3Uploader s3Uploader;

    private final RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/test/student")
    @ResponseBody
    public RedisMemberDto findStudent(){
        ValueOperations<String,Object> valueOperations=redisTemplate.opsForValue();
        return (RedisMemberDto)valueOperations.get("key");
    }

    @GetMapping("/uploadTest")
    public String index(){
        ValueOperations<String,Object> valueOperations=redisTemplate.opsForValue();
        RedisMemberDto redisMemberDto=new RedisMemberDto();
        redisMemberDto.setAge(10);
        redisMemberDto.setName("홍길동");
        valueOperations.set("key",redisMemberDto);

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
