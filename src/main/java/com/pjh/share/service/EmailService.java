package com.pjh.share.service;



import com.pjh.share.util.EmailHandler;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String FROM_ADDRESS;

    public void sendAuthMail(String to,String authString) throws Exception{

        String title="이메일 인증번호입니다";
        EmailHandler emailHandler=new EmailHandler(emailSender);
        emailHandler.setFrom(FROM_ADDRESS);
        emailHandler.setTo(to);
        emailHandler.setSubject(title);
        String htmlContent="<a href='http://localhost:8080/email/auth/"+authString+"'"+">인증하기</a>";
        System.out.println("authString : "+authString);
        System.out.println("htmlContent : "+htmlContent);
        emailHandler.setText(htmlContent,true);
        emailHandler.send();
    }

}
