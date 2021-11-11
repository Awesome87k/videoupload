package com.genesislabs.video.service;

import com.genesislabs.exception.BadRequestException;
import com.genesislabs.exception.TokenInfoNotfoundException;
import com.genesislabs.video.dto.req.JoinUserInfoReqDTO;
import com.genesislabs.video.dto.req.RemoveUserReqDTO;
import com.genesislabs.video.entity.TokenEntity;
import com.genesislabs.video.entity.UserEntity;
import com.genesislabs.video.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

/**
 * 토큰에 세팅된 유저 정보로 회원정보를 조회하는 UserDetailsService를 재정의 합니다.
 */
@Log4j2
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    UserDetails userDetails = new UserDetails() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            //TODO DB에서 사용자정보 조회후 리턴
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

    public UserEntity loadUserByUserInfo(String _email, String _pw) {
        UserEntity userEntity = userInfoRepository.findUserByUserInfo(_email, _pw);
        if(ObjectUtils.isEmpty(userEntity))
            throw new UsernameNotFoundException("사용자 정보가 올바르지 않습니다.");

        return userEntity;
    }

    public String loadUserinfoInfoByToken(String _refreshToken) throws TokenInfoNotfoundException {
        UserEntity userEntity = userInfoRepository.findUserByTokeninfo(_refreshToken);
        if(ObjectUtils.isEmpty(userEntity))
            throw new TokenInfoNotfoundException();

        return userEntity.getVu_email();
    }

    @Transactional
    public void patchTokenInfo(String _refreshToken, String _email, long _expireTm) {
        TokenEntity tokenEntity = userInfoRepository.findTokenInfoByEmail(_email);
        if(ObjectUtils.isEmpty(tokenEntity)) {
            UserEntity userEntity = userInfoRepository.findUserByUserInfo(_email);
            tokenEntity = TokenEntity.builder()
                    .vu_idx(userEntity.getVu_idx())
                    .vt_refresh_token(_refreshToken)
                    .vt_expiredtm(_expireTm)
                    .build();
            userInfoRepository.patchTokenInfo(tokenEntity);
        } else
            tokenEntity.setVt_refresh_token(_refreshToken);
    }

    public boolean addJoinUser(JoinUserInfoReqDTO _joinUserDTO) {
        boolean resltTf = false;

        if(!ObjectUtils.isEmpty(_joinUserDTO)) {
            //email 중복확인
            UserEntity userEntity = userInfoRepository.findDuplicateIdByEmail(_joinUserDTO.getEmail());
            if (!ObjectUtils.isEmpty(userEntity))
                throw new BadRequestException("요청한 Email이 중복되었습니다.");

            userEntity = UserEntity.builder()
                    .vu_email(_joinUserDTO.getEmail())
                    .vu_pw(_joinUserDTO.getPw())
                    .vu_name(_joinUserDTO.getName())
                    .vu_phonenum(_joinUserDTO.getPhonenum())
                    .build();
            resltTf = userInfoRepository.addJoinUser(userEntity);
        } else
            throw new BadRequestException("회원가입 요청데이터가 존재하지 않습니다.");

        return resltTf;
    }

    @Transactional
    public boolean removeUserWithEmail(String _email) {
        boolean resltTf = false;

        if(!ObjectUtils.isEmpty(_email)) {
            //email 중복확인
            UserEntity userEntity = userInfoRepository.findDuplicateIdByEmail(_email);
            if (ObjectUtils.isEmpty(userEntity))
                throw new BadRequestException("탈퇴요청한 사용자가 존재하지 않습니다.");
            else {
                userEntity.setVu_del_yn("N");
                resltTf = true;
            }
        } else
            throw new BadRequestException("탈퇴요청 정보가 존재하지 않습니다.");

        return resltTf;
    }
}