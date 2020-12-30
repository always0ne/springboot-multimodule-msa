package com.restapi.template.api.user.exception;

/**
 * 엑세스할 수 없는 사용자 예외
 * 존재하지 않거나 제제당한 계정입니다.
 *
 * @author always0ne
 * @version 1.0
 */
public class InvalidUserException extends RuntimeException {
  /**
   * 존재하지 않는 댓글입니다.
   */
  public InvalidUserException() {
    super("존재하지 않거나 제제당한 계정입니다.");
  }
}
