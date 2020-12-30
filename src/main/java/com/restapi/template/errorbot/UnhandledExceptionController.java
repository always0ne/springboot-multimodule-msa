package com.restapi.template.errorbot;

import com.restapi.template.api.common.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 처리되지 않은 예외를 로깅하고, 보고하기 위한 Exception Handler.
 *
 * @author always0ne
 * @version 1.0
 */
@ControllerAdvice
public class UnhandledExceptionController {

  private static final Logger logger = LoggerFactory.getLogger(UnhandledExceptionController.class);

  /**
   * 걸러지지 않은 예외 로깅.
   *
   * @param throwable 걸러지지 않은 예외
   * @return ErrorResponse
   */
  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorResponse reportError(Throwable throwable) {
    logging(throwable);
    return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "2000",
        "예상치 못한 에러가 발생했습니다. 빨리 고치겠습니다.");
  }

  /**
   * ERROR 레벨로 로그 남기기.
   *
   * @param throwable 걸러지지 않은 예외
   */
  protected void logging(Throwable throwable) {
    if (logger.isErrorEnabled()) {
      if (throwable.getMessage() != null) {
        logger.error(throwable.getMessage(), throwable);
      } else {
        logger.error("ERROR", throwable);
      }
    }
  }
}
