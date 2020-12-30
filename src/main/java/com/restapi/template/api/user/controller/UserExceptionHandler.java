package com.restapi.template.api.user.controller;

import com.restapi.template.api.common.response.ErrorResponse;
import com.restapi.template.api.user.exception.InvalidUserException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 사용자 서비스에서 발생하는 Exception Handler.
 *
 * @author always0ne
 * @version 1.0
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionHandler {
  /**
   * 존재하지 않거나 제제된 화원입니다.
   *
   * @param exception 없는 댓글 예외
   * @return NOT_FOUND
   */
  @ExceptionHandler(InvalidUserException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleCommentNotFound(InvalidUserException exception) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST, "1001", exception.getMessage());
  }
}
