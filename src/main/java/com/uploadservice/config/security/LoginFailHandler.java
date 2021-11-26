package com.uploadservice.config.security;

import com.uploadservice.common.enums.FailMessage;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
    public static final String LOGIN_FAIL_MESSAGE = "LOGIN_FAIL_MESSAGE";
    public static final String LOGIN_FAIL_REDIRECT_URI = "/page/login-view";

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request
            , HttpServletResponse response
            , AuthenticationException exception
    ) throws IOException {
        String message = null;
        if ( exception instanceof AuthenticationServiceException )
            message = FailMessage.NOT_FOUND_USER.getMessage();
        else if( exception instanceof BadCredentialsException )
            message = FailMessage.INVALUD_LOGIN_FAIL.getMessage();
        else if( exception instanceof UsernameNotFoundException)
            message = FailMessage.INVALUD_LOGIN_FAIL.getMessage();

        if (message != null) {
            final FlashMap flashMap = new FlashMap();
            flashMap.put(LOGIN_FAIL_MESSAGE, message);
            final FlashMapManager flashMapManager = new SessionFlashMapManager();
            flashMapManager.saveOutputFlashMap(flashMap, request, response);
        }
        response.sendRedirect(LOGIN_FAIL_REDIRECT_URI);
    }
}
