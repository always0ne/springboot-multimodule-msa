package com.restapi.template.security.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 인증토큰 발급 요청.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInRequest {
  /**
   * 사용자 ID.
   */
  private String id;
  /**
   * 사용자 비밀번호.
   */
  private String password;
}
