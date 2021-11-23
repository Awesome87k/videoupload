package com.genesislabs.config.security;

import com.genesislabs.video.service.CustomUserDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    HttpServletRequest request;
    CustomUserDetailService userDetailService;

    @Autowired
    public void setCustomUserDetailService(CustomUserDetailService _userDetailService) {
        this.userDetailService = _userDetailService;
    }

    @Override
    public Authentication authenticate(Authentication _auth) throws AuthenticationException {
        String email = (String) _auth.getPrincipal();
        String password = (String) _auth.getCredentials();
        User user = (User) userDetailService.loadUserByUsername(email);
        if(!user.getPassword().equals(password))
            throw new BadCredentialsException("'" + email + "' Invalid password");

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }
}
