package com.genesislabs.video.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 토큰에 세팅된 유저 정보로 회원정보를 조회하는 UserDetailsService를 재정의 합니다.
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    //    private final UserJpaRepo userJpaRepo;
    UserDetails userDetails = new UserDetails() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return null;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    };

    public UserDetails loadUserByUsername(String _userId) throws UsernameNotFoundException {
        return userDetails;
    }
}