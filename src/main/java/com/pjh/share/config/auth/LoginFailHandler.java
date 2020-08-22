package com.pjh.share.config.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
    private static final String LOGIN_FAIL_MSG="loginFailMsg";
    private static final String FAILURE_URL="/login?error=true";
    private Logger logger= LoggerFactory.getLogger(getClass());
    @Override
    public void onAuthenticationFailure(HttpServletRequest request
            , HttpServletResponse response
            , AuthenticationException exception) throws IOException, ServletException {
        if(exception instanceof AuthenticationServiceException){
            request.setAttribute(LOGIN_FAIL_MSG,"존재하지 않는 사용자입니다.");
        }else if(exception instanceof BadCredentialsException){
            request.setAttribute(LOGIN_FAIL_MSG,"아이디 또는 비밀번호가 틀립니다.");
        }else if(exception instanceof LockedException){
            request.setAttribute(LOGIN_FAIL_MSG,"잠긴 계정입니다.");
        }else if(exception instanceof DisabledException){
            request.setAttribute(LOGIN_FAIL_MSG,"비활성화된 계정입니다.");
        }else if(exception instanceof AccountExpiredException){
            request.setAttribute(LOGIN_FAIL_MSG,"만료된 계정입니다.");
        }else if(exception instanceof DisabledException){
            request.setAttribute(LOGIN_FAIL_MSG,"비밀번호가 만료됬습니다.");
        }
        logger.info("login error : "+exception.getMessage());
        RequestDispatcher dispatcher=request.getRequestDispatcher(FAILURE_URL);
        dispatcher.forward(request,response);
    }
}
