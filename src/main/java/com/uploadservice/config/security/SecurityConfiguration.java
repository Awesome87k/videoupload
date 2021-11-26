package com.uploadservice.config.security;

import com.uploadservice.video.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected static final String[] PUBLIC_URI = {
            "/js/**","/vendors/**","/css/**"
            , "/favicon.ico", "/error"
            , "/page/login-view"
            , "/page/join-view"
            , "/data/user/login"
            , "/data/user/logout"
            , "/data/user/**"
            , "/data/user"
    };

    private LoginSuccessHandler successHandler;
    private LoginFailHandler failHandler;
    private CustomAuthenticationManager authenticationProvider;
    private CustomUserDetailService userDetailService;

    @Autowired
    public void setLoginSuccessHandler(LoginSuccessHandler _loginSuccessHandler) {
        this.successHandler = _loginSuccessHandler;
    }
    @Autowired
    public void setLoginFailHandler(LoginFailHandler _loginFailHandler) {
        this.failHandler = _loginFailHandler;
    }
    @Autowired
    public void setCustomAuthenticationManager(CustomAuthenticationManager _customAuthenticationManager) {
        this.authenticationProvider = _customAuthenticationManager;
    }
    @Autowired
    public void setCustomUserDetailService(CustomUserDetailService _customUserDetailService) {
        this.userDetailService = _customUserDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * TODO Security 설정 사용자의 유저네임과 패스워드가 맞는지 검증
     * @param _auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder _auth) throws Exception {
        _auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
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
            .sessionManagement()    // stateless한 세션 정책 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().formLogin()
                .loginPage("/page/login-view")
                .permitAll()
            .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/data/logout"))
                .logoutSuccessUrl("/page/login-view")
                .deleteCookies("JSESSIONID")
                .deleteCookies("refreshToken")
                .deleteCookies("accessToken")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            .and().cors()
            .and().authorizeRequests()
                .antMatchers(PUBLIC_URI).permitAll()
                .anyRequest().access("@authorizationChecker.check(request, authentication)")
            .and().addFilterBefore(customAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 시큐리티 설정 제외 url
     * @param _web
     */
    @Override
    public void configure(WebSecurity _web) {
        _web.ignoring().antMatchers(PUBLIC_URI);
    }

    @Bean
    public CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter() {
        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter("/data/user/login");
        filter.setAuthenticationManager(authenticationProvider);
        filter.setAuthenticationFailureHandler(failHandler);
        filter.setAuthenticationSuccessHandler(successHandler);
        return filter;
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

}