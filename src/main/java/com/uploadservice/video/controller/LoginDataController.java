package com.uploadservice.video.controller;

import com.uploadservice.common.DataResponse;
import com.uploadservice.common.DataResponsePattern;
import com.uploadservice.common.enums.FailMessage;
import com.uploadservice.video.dto.req.EditUserInfoReqDTO;
import com.uploadservice.video.dto.req.JoinUserInfoReqDTO;
import com.uploadservice.video.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/data/user")
@RestController
public class LoginDataController extends DataResponsePattern {

    private CustomUserDetailService userDetailsService;

    @Autowired
    public void setCustomUserDetailService(CustomUserDetailService _userDetailsService) {
        this.userDetailsService = _userDetailsService;
    }

    @PostMapping
    public DataResponse joinUser(
            @RequestBody @Valid JoinUserInfoReqDTO _joinUserDTO
    ) {
        boolean reusltTf = userDetailsService.addJoinUser(_joinUserDTO);
        if(reusltTf)
            return super.mvcReponseSuccess("회원 가입에 성공했습니다");
        else
            return super.mvcReponseFail(FailMessage.ADD_ACCOUNT_FAIL.getMessage());
    }

    @PutMapping
    public DataResponse editUser(
            @RequestBody @Valid EditUserInfoReqDTO _editUserDTO
    ) {
        userDetailsService.editUserInfo(_editUserDTO);
        return super.mvcReponseSuccess("회원 정보수정에 성공했습니다");
    }

    @DeleteMapping
    public DataResponse deleteAccount() {
        boolean reusltTf = userDetailsService.removeUserWithEmail();
        if(reusltTf)
            return super.mvcReponseSuccess("회원 탈퇴에 성공했습니다");
        else
            return super.mvcReponseFail(FailMessage.DEL_ACCOUNT_FAIL.getMessage());
    }

}