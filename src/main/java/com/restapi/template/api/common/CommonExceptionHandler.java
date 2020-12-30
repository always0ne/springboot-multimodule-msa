package com.restapi.template.api.common;

import com.restapi.template.api.common.exception.ThisIsNotYoursException;
import com.restapi.template.api.common.response.ErrorResponse;
import java.util.Objects;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 공통적으로 발생하는 Exception Handler.
 *
 * @author always0ne
 * @version 1.0
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonExceptionHandler {
  /**
   * 존재하지 않거나 본인 소유가 아닌 컨텐츠 수정 요청 발생.
   *
   * @param exception 수정권한이 없습니다.
   * @return FORBIDDEN
   */
  @ExceptionHandler(ThisIsNotYoursException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleNotYours(ThisIsNotYoursException exception) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST, "1001", exception.getMessage());
  }

  /**
   * 형식에 맞지 않는 BODY요청이 왔을 때.
   *
   * @param exception 잘못된 데이터입니다.
   * @return BAD_REQUEST
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponse handleNotYours(HttpMessageNotReadableException exception) {
    String message = Objects.requireNonNull(exception.getRootCause()).getMessage();
    return new ErrorResponse(HttpStatus.BAD_REQUEST, "2001", message.split("\\(class")[0]);
  }
}
