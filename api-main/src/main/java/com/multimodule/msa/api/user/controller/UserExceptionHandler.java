package com.multimodule.msa.api.user.controller;

import com.multimodule.msa.exception.InvalidUserException;
import com.multimodule.msa.response.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionHandler {

    @ExceptionHandler(InvalidUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleCommentNotFound(InvalidUserException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "1001", exception.getMessage());
    }
}
