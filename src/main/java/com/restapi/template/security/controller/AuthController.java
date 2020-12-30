package com.restapi.template.security.controller;

import com.restapi.template.security.request.RefreshRequest;
import com.restapi.template.security.request.SignInRequest;
import com.restapi.template.security.request.SignUpRequest;
import com.restapi.template.security.response.RefreshResponse;
import com.restapi.template.security.response.SignInResponse;
import com.restapi.template.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 인증 컨트롤러.
 *
 * @author always0ne
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = MediaTypes.HAL_JSON_VALUE)
public class AuthController {

  private final AuthService authService;

  /**
   * 토큰 발급받기.
   *
   * @param signInRequest 사용자 ID, 비밀번호
   * @return accessToken
   */
  @PostMapping("/signin")
  @ResponseStatus(HttpStatus.OK)
  public SignInResponse signIn(
      @RequestBody SignInRequest signInRequest
  ) {
    return this.authService.signIn(signInRequest.getId(), signInRequest.getPassword());
  }

  /**
   * 회원 가입하기.
   *
   * @param signUpRequest 사용자 ID, 비밀번호, 이름
   * @return accessToken
   */
  @PostMapping("/signup")
  @ResponseStatus(HttpStatus.OK)
  public SignInResponse signUp(
      @RequestBody SignUpRequest signUpRequest
  ) {
    return this.authService
        .signUp(signUpRequest.getId(), signUpRequest.getPassword(), signUpRequest.getName());
  }

  /**
   * 아이디 중복 체크하기.
   *
   * @param userId 중복확인할  ID
   * @return 사용가능 여부
   */
  @GetMapping("/checkid/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public String idCheck(
      @PathVariable String userId
  ) {
    this.authService.idCheck(userId);
    return "사용가능한 아이디입니다.";
  }

  /**
   * RefreshToken 으로 AccessToken 재발급 받기.
   *
   * @param refreshRequest 토큰 갱신 요청
   * @return AccessToken
   */
  @PostMapping("/refresh")
  @ResponseStatus(HttpStatus.OK)
  public RefreshResponse getNewAccessToken(
      @RequestBody RefreshRequest refreshRequest
  ) {
    return this.authService.refreshAccessToken(refreshRequest);
  }
}
