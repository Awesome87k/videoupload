package com.genesislabs.config.security;

import com.genesislabs.common.enums.URIEnum;
import com.genesislabs.video.entity.UserEntity;
import com.genesislabs.video.service.CustomUserDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Configuration
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final String  ADMIN_MAIN_URL_REDIRECT = "/page/main-view";
    private final String  MEMBER_MAIN_URL_REDIRECT = "/page/main-view";

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private JwtComponent jwtComponent;

    @Autowired
    private CookieComponent cookieComponent;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest _req
            , HttpServletResponse _res
            , Authentication _auth
    ) throws IOException {
        Object pricipalObj = _auth.getPrincipal();
        String userEmail = ((User) pricipalObj).getUsername();
        UserEntity user = userDetailsService.loadUserByUserInfo(userEmail);
        String token = jwtComponent.generateToken(user.getVu_email());
        String refreshJwt = jwtComponent.generateRefreshToken(user);
        Cookie accessToken = cookieComponent.createCookie(JwtComponent.ACCESS_TOKEN_NAME, token);
        Cookie refreshToken = cookieComponent.createCookie(JwtComponent.REFRESH_TOKEN_NAME, refreshJwt);
        String redirectUri = (user.getVu_level().equals("A") ? URIEnum.ADMIN_MAIN_URL_REDIRECT.getMessage() : URIEnum.MEMBER_MAIN_URL_REDIRECT.getMessage());
        //실서비스의 경우 redis를 통해 토큰관리
//            redisComponent.setDataExpire(refreshJwt, user.getVu_email(), JwtComponent.REFRESH_TOKEN_VALIDATION_SECOND);
        userDetailsService.patchTokenInfo(refreshJwt, user.getVu_email(), JwtComponent.REFRESH_TOKEN_VALIDATION_SECOND);
        _res.addCookie(accessToken);
        _res.addCookie(refreshToken);
        _res.sendRedirect(redirectUri);   //실 서비스라면 DB를 통해 사용자의 설정된 메인메뉴로 이동
    }
}
