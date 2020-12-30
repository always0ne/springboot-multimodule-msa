package com.restapi.template.api.user.data.dto;

import lombok.AllArgsConstructor;

/**
 * 사용자 ID 데이터 전송 객체 ID 중복 확인 기능에 사용된다.
 *
 * @author always0ne
 * @version 1.0
 */
@AllArgsConstructor
public class UserIdDto {
  /**
   * 사용자 ID.
   */
  private String userId;
}
