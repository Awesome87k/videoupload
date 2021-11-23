package com.genesislabs.common.enums;

import lombok.Getter;

@Getter
public enum FailMessage {
    NOT_FOUND_USER("존재하지 않는 사용자입니다."),
    INVALUD_LOGIN_FAIL("아이디 또는 비밀번호가 틀립니다."),
    DEL_ACCOUNT_FAIL("회원가입에 실패했습니다."),
    ADD_ACCOUNT_FAIL("사용자 등록에 실패했습니다."),
    FIND_ACCOUNT_FAIL("사용자 정보조회에 실패했습니다."),
    FILE_UPLOAD_FAIL("파일업로드에 실패했습니다."),
    FILE_LIST_SEARCH_FAIL("데이터 조회에 실패했습니다."),
    FILE_UPLOAD_EMPTY_FAIL("파일요청이 올바르지 않습니다.");

    private String message;

    FailMessage(String message) {
        this.message = message;
    }
}