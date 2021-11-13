package com.genesislabs.common.enums;

import lombok.Getter;

@Getter
public enum LoginFail {
    NOT_FOUND_USER("존재하지 않는 사용자입니다."),
    INVALUD_LOGIN_INFO("아이디 또는 비밀번호가 틀립니다.");

    private String message;

    LoginFail(String message) {
        this.message = message;
    }
}