package com.genesislabs.config.security;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_URI = {
            "/page/login_view",
            "/data/login_access"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("SHA-256", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
        return new DelegatingPasswordEncoder("SHA-256", encoders);
    }

    /**
     * 시큐리티 규칙
     * @param _http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity _http) throws Exception {
        _http
            .csrf().disable()
            .formLogin().disable()
            .sessionManagement()    // stateless한 세션 정책 설정
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().cors()
            .and().authorizeRequests()
                .antMatchers(PUBLIC_URI).permitAll()
                .anyRequest().authenticated()
            .and().exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint());    // 인증 오류 발생 시 처리를 위한 핸들러 추가
        // security필터이후  토큰 인증 필터 추가 ( TokenAuthenticationFilter > @repository를 등록하지 않고 계층구조를 만들기위해 bean으로 처리 )
        _http.cors()
                .and().addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 시큐리티 설정 제외 url
     * @param _web
     */
    @Override
    public void configure(WebSecurity _web) {
        _web.ignoring().antMatchers(PUBLIC_URI);
    }

    /**
     * 토큰 인증 필터 추가
     * @return TokenAuthenticationFilter
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
}