package com.genesislabs.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증오류 handler
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest _req
            , HttpServletResponse _res
            , AuthenticationException _e
    ) throws IOException {
        _res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
