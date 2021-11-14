package com.genesislabs.config.security;

import com.genesislabs.common.enums.URIEnum;
import com.genesislabs.common.exception.BusinessException;
import com.genesislabs.common.exception.ForbiddenException;
import com.genesislabs.video.entity.UserEntity;
import com.genesislabs.video.service.CustomUserDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Component
public class AuthorizationChecker {

    @Autowired
    CustomUserDetailService userDetailService;

    /**
     * 사용자의 메뉴접근권한을 확인한다.( 실운영 서비스에서는 DB를 활용하여 사용자메뉴 접근권한을 관리한다 )
     * @param _req
     * @return boolean
     */
    public boolean check(HttpServletRequest _req, Authentication _authentication) {
        if(ObjectUtils.isEmpty(_authentication))
            throw new UsernameNotFoundException("사용자 정보가 존재하지 않습니다.");

        Object pricipalObj = _authentication.getPrincipal();
        String userEmail = ((User) pricipalObj).getUsername();
        UserEntity userEntity = userDetailService.loadUserByUserInfo(userEmail);
        String level = userEntity.getVu_level();

        if(level.equals("A")) {
            if(_req.getRequestURI().equals(URIEnum.MEMBER_MAIN_URL_REDIRECT.getMessage()))
                return false;
            else
                return true;
        } else if(level.equals("M")) {
            if(_req.getRequestURI().equals(URIEnum.ADMIN_MAIN_URL_REDIRECT.getMessage()))
                return false;
            else
                return true;
        } else
            throw new BusinessException("지정된 user레벨이 아닙니다. 'v_user.vu_level'정보를 확인해주세요");

    }
}
