package com.restapi.template.api.community.post.controller;

import com.restapi.template.api.common.response.ErrorResponse;
import com.restapi.template.api.community.post.exception.PostNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 게시글 서비스에서 발생하는 Exception Handler.
 *
 * @author always0ne
 * @version 1.0
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PostExceptionHandler {
  /**
   * 없는 게시글 예외 발생.
   *
   * @param exception 없는 게시글 예외
   * @return NOT_FOUND
   */
  @ExceptionHandler(PostNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorResponse handleCommentNotFound(PostNotFoundException exception) {
    return new ErrorResponse(HttpStatus.NOT_FOUND, "1101", exception.getMessage());
  }
}
