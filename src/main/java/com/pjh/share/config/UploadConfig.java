package com.pjh.share.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.MultipartConfigElement;

@Configuration
public class UploadConfig {
    //최대 업로드 크기
    @Value("${spring.servlet.multipart.max-file-size}")
    private DataSize maxFileSize;

    @Value("${spring.servlet.multipart.max-request-size}")
    private DataSize maxRequestSize;

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(maxFileSize);
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        return multipartResolver;
    }
}
