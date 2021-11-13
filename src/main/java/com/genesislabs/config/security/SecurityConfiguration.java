package com.genesislabs.config.security;

import com.genesislabs.video.service.CustomUserDetailService;
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
            "/", "/css/**" , "/js/**", "/favicon.ico", "/error"
            , "/page/login-view"
            , "/page/join-view"
            , "/data/user/login"
            , "/data/user/logout"
            , "/data/user/join"
    };

    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    private LoginFailHandler failHandler;

    @Autowired
    private CustomAuthenticationManager authenticationProvider;

    @Autowired
    private CustomUserDetailService userDetailService;

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