package com.genesislabs.video.service;

import com.genesislabs.common.exception.*;
import com.genesislabs.config.security.CookieComponent;
import com.genesislabs.video.dto.req.EditUserInfoReqDTO;
import com.genesislabs.video.dto.req.JoinUserInfoReqDTO;
import com.genesislabs.video.entity.TokenEntity;
import com.genesislabs.video.entity.UserEntity;
import com.genesislabs.video.repository.UserInfoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 회원정보를 조회하는 UserDetailsService를 재정의 합니다.
 */
@Log4j2
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final String ADMIN_USER_LEVEL = "A";
    private UserInfoRepository userInfoRepository;
    private CookieComponent cookieComponent;

    @Autowired
    public void setUserInfoRepository(UserInfoRepository _userInfoRepository) {
        this.userInfoRepository = _userInfoRepository;
    }

    @Autowired
    public void setCookieComponent(CookieComponent _cookieComponent) {
        this.cookieComponent = _cookieComponent;
    }

    @Override
    public UserDetails loadUserByUsername(String _email) throws UsernameNotFoundException {
        UserEntity userInfo = userInfoRepository.findUserByUserInfo(_email);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if(ObjectUtils.isEmpty(userInfo))
            throw new UsernameNotFoundException("'" + _email + "' not found username");

        if(userInfo.getVu_level().equals(ADMIN_USER_LEVEL))
            grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
        else
            grantedAuthorities.add(new SimpleGrantedAuthority("MEMBER"));

        return new User(userInfo.getVu_email(), userInfo.getVu_pw(), userInfo.getVu_del_yn().equals("N"), true, true, true, grantedAuthorities);
    }

    public UserEntity loadUserInfoByEmail(String _email) {
        UserEntity userEntity = userInfoRepository.findUserByUserInfo(_email);
        if(ObjectUtils.isEmpty(userEntity))
            throw new UsernameNotFoundException("사용자 정보가 올바르지 않습니다.");

        return userEntity;
    }

    public UserEntity loadUserinfoInfoByToken(String _refreshToken) throws TokenInfoNotfoundException {
        UserEntity userEntity = userInfoRepository.findUserByTokeninfo(_refreshToken);
        if(ObjectUtils.isEmpty(userEntity))
            throw new TokenInfoNotfoundException();

        return userEntity;
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
                    .vu_level(_joinUserDTO.getLevel())
                    .build();
            resltTf = userInfoRepository.addJoinUser(userEntity);
        } else
            throw new BadRequestException("회원가입 요청데이터가 존재하지 않습니다.");

        return resltTf;
    }

    @Transactional
    public void editUserInfo(EditUserInfoReqDTO _joinUserDTO) {
        UserEntity userEntity = cookieComponent.findUserInfoByCookie();
        if (ObjectUtils.isEmpty(userEntity))
            throw new UsernameNotFoundException("사용자 정보를 확인할 수 없습니다.");

        userEntity.setVu_name(_joinUserDTO.getName());
        userEntity.setVu_phonenum(_joinUserDTO.getPhonenum());
        userEntity.setVu_level(_joinUserDTO.getLevel());
    }

    @Transactional
    public boolean removeUserWithEmail() {
        UserEntity userEntity = cookieComponent.findUserInfoByCookie();
        if (ObjectUtils.isEmpty(userEntity))
            throw new BadRequestException("탈퇴요청한 사용자가 존재하지 않습니다.");
        else {
            userEntity.setVu_email(userEntity.getVu_email()+"#DEL");
            userEntity.setVu_del_yn("Y");
            return true;
        }
    }
}