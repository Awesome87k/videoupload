package com.genesislabs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Bad Request Exception 정의
 * RequestParam에 잘못된 자원을 요청했을때 발생시키는 Exception 클래스
 * httpstatus 400대 부여
 */
@ResponseStatus( HttpStatus.BAD_REQUEST )
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorCode;

    public BadRequestException() {
        super();
    }

    public BadRequestException( String message, String errorCode ) {
        super( message );
        this.errorCode = errorCode;
    }
    public String getErrorCode(){
        return this.errorCode;
    }
}
