package com.genesislabs.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Value("${hereIp:}")
    String localIP;

    @Around("execution(@org.springframework.transaction.annotation.Transactional * *(..))")
    public Object transactionLogger( ProceedingJoinPoint _pjp ) throws Throwable {
        StringBuilder sb = getParameters(_pjp);
        log.info("Transaction call - location : {}, parm : {}", _pjp, sb);
        Object result = _pjp.proceed();
        return result;
    }

    @AfterThrowing(
        pointcut = "execution(* com.genesislabs.*.service.*.*(..)) ",
        throwing = "_e"
    )
    public void afterThrowing(JoinPoint _jp, Exception _e) {
        StringBuilder sb = getParameters(_jp);
        log.error("location : {}, param : {}", _jp, sb, _e);
    }

    private StringBuilder getParameters(JoinPoint _jp) {
        Object[] args = _jp.getArgs();
        StringBuilder sb = new StringBuilder();
        if (args != null)
            for (Object arg : args)
                if (arg != null) {
                    if (sb.length() > 0)
                        sb.append(",");
                    sb.append(arg.toString());
                }
        return sb;
    }

}