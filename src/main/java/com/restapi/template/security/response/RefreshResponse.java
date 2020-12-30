package com.restapi.template.security.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 인증토큰 갱신 응답.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshResponse {
  /**
   * accessToken.
   */
  private String accessToken;
}
