package com.genesislabs.common;

import com.genesislabs.exception.BadRequestException;
import com.genesislabs.exception.BusinessException;
import com.genesislabs.exception.ForbiddenException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.atomic.AtomicReference;

/**
 * ResponseEntity 전역 예외처리
 */
@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 400
     * Request에 부적절한 자원을 전달시 리턴
     * @param _ex
     * @return DataResponse
     */
    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity badRequestException(BadRequestException _ex) {
        DataResponse dataRes = new DataResponse();
        dataRes.setMessage(_ex.getMessage());
        dataRes.setResult(false);
        return new ResponseEntity(dataRes, HttpStatus.BAD_REQUEST);
    }

    /**
     * 401
     * Request에 사용자 인증정보가 올바르지 않을경우 리턴
     * @param _ex
     * @return DataResponse
     */
    @ExceptionHandler({ UsernameNotFoundException.class })
    public ResponseEntity userNotFoundException(UsernameNotFoundException _ex) {
        DataResponse dataRes = new DataResponse();
        dataRes.setMessage(_ex.getMessage());
        dataRes.setResult(false);
        return new ResponseEntity(dataRes, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 403
     * 권한이 없는 접근일 경우 리턴
     * @param _ex
     * @return DataResponse
     */
    @ExceptionHandler({ ForbiddenException.class })
    public ResponseEntity forbiddenException(ForbiddenException _ex){
        _ex.printStackTrace();
        DataResponse dataRes = new DataResponse();
        dataRes.setMessage(_ex.getMessage());
        dataRes.setResult(false);
        return new ResponseEntity(dataRes, HttpStatus.FORBIDDEN);
    }

    /**
     * 400
     * Request에 부적절한 method를 전달시 리턴
     * @param _ex
     * @return DataResponse
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException _ex
            , HttpHeaders _headers
            , HttpStatus _status
            , WebRequest _request
    ) {
        AtomicReference<String> errMsg = new AtomicReference<>("");
        _ex.getBindingResult().getAllErrors().forEach((error) -> {
            String msg = error.getDefaultMessage();
            errMsg.set(msg);
        });

        DataResponse dataRes = new DataResponse();
        dataRes.setMessage(errMsg.toString());
        dataRes.setResult(false);
        return new ResponseEntity(dataRes, HttpStatus.BAD_REQUEST);
    }

    /**
     * 500
     * CUD 작업시 정상적으로 처리가 안됬을 떄
     * @param _ex
     * @return DataResponse
     */
    @ExceptionHandler({ BusinessException.class })
    public ResponseEntity businessException(BusinessException _ex){
        log.error(_ex);
        DataResponse dataRes = new DataResponse();
        dataRes.setMessage(_ex.getMessage());
        dataRes.setResult(false);
        return new ResponseEntity(dataRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity exception(Exception _ex){
        log.error(_ex);
        DataResponse dataRes = new DataResponse();
        dataRes.setMessage("서버 에러. 관리자에게 문의바랍니다.");
        dataRes.setResult(false);
        return new ResponseEntity(dataRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
