package com.genesislabs.config.security;

import com.genesislabs.video.entity.UserEntity;
import com.genesislabs.video.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class CookieComponent {

    @Autowired
    private HttpServletRequest request;
    private CustomUserDetailService userDetailsService;

    @Autowired
    public void setCustomUserDetailService(CustomUserDetailService _userDetailsService) {
        this.userDetailsService = _userDetailsService;
    }

    public Cookie createCookie(String _cookieNm, String _value){
        Cookie token = new Cookie(_cookieNm, _value);
        token.setHttpOnly(true);
        token.setMaxAge((int)JwtComponent.TOKEN_VALIDATION_SECOND);
        token.setPath("/");
        return token;
    }

    public Cookie getCookie(HttpServletRequest _req, String _cookieNm){
        final Cookie[] cookies = _req.getCookies();
        if(cookies==null) return null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(_cookieNm))
                return cookie;
        }
        return null;
    }

    public UserEntity findUserInfoByCookie() {
        Cookie refreshCookie = getCookie(request, JwtComponent.REFRESH_TOKEN_NAME);
        String refeshToken = refreshCookie.getValue();
        UserEntity userEntity = userDetailsService.loadUserinfoInfoByToken(refeshToken);
        if(ObjectUtils.isEmpty(userEntity)) {
            throw new UsernameNotFoundException("사용자 정보가 존재하지 않습니다. refreshToken : " + refeshToken);
        }
        return userEntity;
    }
}
