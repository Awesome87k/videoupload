package com.genesislabs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BusinessException 정의
 * 요청처리중 발생할 경우 발생시키는 Exception 클래스
 * httpstatus 500대 부여
 */
@ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BusinessException() {
        super();
    }

    public BusinessException( String message) {
        super( message );
    }
}