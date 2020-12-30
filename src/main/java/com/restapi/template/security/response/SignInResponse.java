package com.restapi.template.security.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 인증토큰 발급 응답.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResponse {
  /**
   * accessToken.
   */
  private String accessToken;
  /**
   * accessToken.
   */
  private String refreshToken;
}
