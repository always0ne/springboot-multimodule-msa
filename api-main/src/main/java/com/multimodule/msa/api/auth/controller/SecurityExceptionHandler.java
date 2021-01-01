package com.multimodule.msa.api.auth.controller;

import com.multimodule.msa.exception.CantSignInException;
import com.multimodule.msa.exception.IdAlreadyExistsException;
import com.multimodule.msa.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * 회원 인증상에서 발생하는 Exception Handler
 *
 * @author always0ne
 * @version 1.0
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityExceptionHandler {

    @ExceptionHandler(IdAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public ErrorResponse handleIdExists(IdAlreadyExistsException exception) {
        return new ErrorResponse(HttpStatus.ACCEPTED, "0001", exception.getMessage());
    }

    @ExceptionHandler(CantSignInException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleUserNotFound(CantSignInException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "0002", exception.getMessage());
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleSignature(SignatureException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "0003", "유효하지 않은 토큰입니다.");
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleMalformedJwt(MalformedJwtException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "0004", "손상된 토큰입니다.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorResponse handleTokenExpired(ExpiredJwtException exception) {
        return new ErrorResponse(HttpStatus.FORBIDDEN, "0006", "만료된 토큰입니다.");
    }
}
