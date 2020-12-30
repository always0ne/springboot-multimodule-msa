package com.restapi.template.api.common.exception;

public class ThisIsNotYoursException extends RuntimeException {
  public ThisIsNotYoursException() {
    super("존재하지 않거나 수정 권한이 없습니다.");
  }
}
