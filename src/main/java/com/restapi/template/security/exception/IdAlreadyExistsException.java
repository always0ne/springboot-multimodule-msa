package com.restapi.template.security.exception;

/**
 * 아이디 중복 예외
 * ID: userId 이미 사용중인 아이디입니다.
 *
 * @author always0ne
 * @version 1.0
 */
public class IdAlreadyExistsException extends RuntimeException {
  /**
   * ID: userId 이미 사용중인 아이디입니다.
   *
   * @param userId 이미 사용중인 아이디
   */
  public IdAlreadyExistsException(String userId) {
    super("ID: " + userId + " 이미 사용중인 아이디입니다.");
  }
}
