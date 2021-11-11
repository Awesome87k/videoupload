package com.genesislabs.video.controller;

import com.genesislabs.common.DataResponse;
import com.genesislabs.common.DataResponsePattern;
import com.genesislabs.config.security.CookieComponent;
import com.genesislabs.config.security.JwtComponent;
import com.genesislabs.config.security.RedisComponent;
import com.genesislabs.video.dto.req.JoinUserInfoReqDTO;
import com.genesislabs.video.dto.req.LoginUserInfoReqDTO;
import com.genesislabs.video.dto.req.RemoveUserReqDTO;
import com.genesislabs.video.entity.UserEntity;
import com.genesislabs.video.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RequestMapping(value = "/data/user/")
@RestController
@RequiredArgsConstructor
public class LoginDataController extends DataResponsePattern {

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    private JwtComponent jwtComponent;

    @Autowired
    private CookieComponent cookieComponent;

    @Autowired
    private RedisComponent redisComponent;

    @GetMapping("login")
    public DataResponse login(
            @RequestBody @Valid LoginUserInfoReqDTO _info,
            HttpServletResponse _res,
            BindingResult bindingResult
    ) {
        if(!ObjectUtils.isEmpty(bindingResult.getAllErrors()))
            return super.mvcReponseFail("요청정보가 올바르지 않습니다."+bindingResult.getAllErrors());

        UserEntity user = userDetailsService.loadUserByUserInfo(_info.getEmail(), _info.getPassword());
        if(ObjectUtils.isEmpty(user))
            return super.mvcReponseFail("로그인정보가 올바르지 않습니다.");

        try {
            String token = jwtComponent.generateToken(user);
            String refreshJwt = jwtComponent.generateRefreshToken(user);
            Cookie accessToken = cookieComponent.createCookie(JwtComponent.ACCESS_TOKEN_NAME, token);
            Cookie refreshToken = cookieComponent.createCookie(JwtComponent.REFRESH_TOKEN_NAME, refreshJwt);
            //실서비스의 경우 redis를 통해 토큰관리
//            redisComponent.setDataExpire(refreshJwt, user.getVu_email(), JwtComponent.REFRESH_TOKEN_VALIDATION_SECOND);
            userDetailsService.patchTokenInfo(refreshJwt, user.getVu_email(), JwtComponent.REFRESH_TOKEN_VALIDATION_SECOND);
            _res.addCookie(accessToken);
            _res.addCookie(refreshToken);

            return super.mvcReponseSuccess("로그인에 성공했습니다");
        } catch (Exception _e) {
            log.error("로그인 실패", _e);
            return super.mvcReponseFail("로그인에 실패했습니다.\n관리자에게 문의해주세요");
        }
    }

    @PostMapping("join")
    public DataResponse joinMember(
            @RequestBody @Valid JoinUserInfoReqDTO _joinUserDTO
    ) {
        boolean reusltTf = userDetailsService.addJoinUser(_joinUserDTO);
        if(reusltTf)
            return super.mvcReponseSuccess("회원 가입에 성공했습니다");
        else
            return super.mvcReponseFail("회원 가입에 실패했습니다.\n관리자에게 문의해주세요");
    }

    @PutMapping("del-account/{_email}")
    public DataResponse deleteAccount(
            @PathVariable String _email
    ) {
        boolean reusltTf = userDetailsService.removeUserWithEmail(_email);
        if(reusltTf)
            return super.mvcReponseSuccess("회원 탈퇴에 성공했습니다");
        else
            return super.mvcReponseFail("회원 탈퇴에 실패했습니다.\n관리자에게 문의해주세요");
    }

}