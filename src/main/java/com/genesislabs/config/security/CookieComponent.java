package com.genesislabs.config.security;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class CookieComponent {
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
}
