package com.restapi.template.api.common.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 응답.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
public class ErrorResponse {
  /**
   * 발생 시각.
   */
  private final LocalDateTime timestamp;
  /**
   * 상태 코드.
   */
  private final int status;
  /**
   * 에러 코드.
   */
  private final String error;
  /**
   * 에러 메시지.
   */
  private final String message;

  /**
   * 에러 메시지.
   *
   * @param httpStatus Http 상태코드
   * @param errCode    에러코드
   * @param message    에러 메시지(클라이언트에 출력될 메시지)
   */
  public ErrorResponse(HttpStatus httpStatus, String errCode, String message) {
    this.timestamp = LocalDateTime.now();
    this.status = httpStatus.value();
    this.error = errCode;
    this.message = message;
  }


}
