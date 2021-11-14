package com.genesislabs.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TokenInfoNotfoundException 정의
 * 저장된 토큰정보를 정상적으로 가져오지 못했을경우 발생시키는 Exception 클래스
 * httpstatus 500대 부여
 */
@ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
public class TokenInfoNotfoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorCode;

    public TokenInfoNotfoundException() {
        super();
    }

    public TokenInfoNotfoundException(String message, String errorCode) {
        super( message );
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return this.errorCode;
    }
}
