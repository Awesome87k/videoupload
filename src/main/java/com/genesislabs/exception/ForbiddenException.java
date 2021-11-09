package com.genesislabs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Bad Request Exception 정의
 * RequestParam에 잘못된 자원을 던져줬을때 발생시키는 Exception 클래스
 * httpstatus 400대 부여
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ForbiddenException(String message) {
        super(message);
    }
}
