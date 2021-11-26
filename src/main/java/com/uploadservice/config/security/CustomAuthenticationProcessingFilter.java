package com.uploadservice.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationProcessingFilter(String defaultFilterProcessesUrl ) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest _request, HttpServletResponse _response) throws AuthenticationException {
        String username = _request.getParameter("access_id");
        String password = _request.getParameter("access_pw");

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
