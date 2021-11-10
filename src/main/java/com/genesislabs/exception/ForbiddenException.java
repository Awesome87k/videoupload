package com.genesislabs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ForbiddenException Exception 정의
 * 사용자의 권한이 없는 요청일 경우 발생하는 Exception 클래스
 * httpstatus 403 부여
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ForbiddenException(String message) {
        super(message);
    }
}
