package com.genesislabs.config.security;

import com.genesislabs.video.entity.UserEntity;
import com.genesislabs.video.service.CustomUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private JwtComponent jwtComponent;

    @Autowired
    private CookieComponent cookieComponent;

    @Autowired
    private RedisComponent redisComponent;

    @Override
    protected void doFilterInternal(
            HttpServletRequest _req
            , HttpServletResponse _res
            , FilterChain _filterChain
    ) throws ServletException, IOException {
        final Cookie jwtToken = cookieComponent.getCookie(_req, JwtComponent.ACCESS_TOKEN_NAME);
        //쿠키정보가 있을경우에만 검증
        if(jwtToken != null)
            tokenFilter(jwtToken, _req, _res);
        _filterChain.doFilter(_req, _res);
    }

    private void tokenFilter(
            Cookie jwtToken
            , HttpServletRequest _req
            , HttpServletResponse _res
    ) {
        String email = null;
        String jwt = null;
        String refreshJwt = null;
        String refreshEmail = null;

        //access token 검증
        try{
            if(jwtToken != null){
                jwt = jwtToken.getValue();
                email = jwtComponent.getUsername(jwt);
            }
            if(email!=null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                if(jwtComponent.validateToken(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(_req));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (ExpiredJwtException _e){
            log.warn(_e.getMessage());
            Cookie refreshToken = cookieComponent.getCookie(_req, JwtComponent.REFRESH_TOKEN_NAME);
            if(refreshToken!=null){
                refreshJwt = refreshToken.getValue();
            }
        }

        //refresh token검증
        try{
            if(refreshJwt != null){
                //실서비스 에선 redis를 구축하여 토큰을 관리해야한다.
//                refreshUname = redisComponent.getData(refreshJwt);
                refreshEmail = userDetailsService.loadUserinfoInfoByToken(refreshJwt);

                if(refreshEmail.equals(jwtComponent.getUsername(refreshJwt))){
                    UserDetails userDetails = userDetailsService.loadUserByUsername(refreshEmail);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(_req));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    UserEntity user = new UserEntity();
                    user.setVu_email(refreshEmail);
                    String newToken =jwtComponent.generateToken(user);

                    Cookie newAccessToken = cookieComponent.createCookie(JwtComponent.ACCESS_TOKEN_NAME,newToken);
                    _res.addCookie(newAccessToken);
                }
            }
        }catch(ExpiredJwtException _e){
            log.error(_e);
        }
    }
}