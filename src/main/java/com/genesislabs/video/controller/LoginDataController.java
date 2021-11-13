package com.genesislabs.video.controller;

import com.genesislabs.common.DataResponse;
import com.genesislabs.common.DataResponsePattern;
import com.genesislabs.video.dto.req.JoinUserInfoReqDTO;
import com.genesislabs.video.dto.req.RemoveUserReqDTO;
import com.genesislabs.video.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/data/user/")
@RestController
@RequiredArgsConstructor
public class LoginDataController extends DataResponsePattern {

    @Autowired
    private CustomUserDetailService userDetailsService;

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

    @DeleteMapping("del-account")
    public DataResponse deleteAccount(
            @Valid RemoveUserReqDTO _removeUserDTO
    ) {
        boolean reusltTf = userDetailsService.removeUserWithEmail(_removeUserDTO);
        if(reusltTf)
            return super.mvcReponseSuccess("회원 탈퇴에 성공했습니다");
        else
            return super.mvcReponseFail("회원 탈퇴에 실패했습니다.\n관리자에게 문의해주세요");
    }

}