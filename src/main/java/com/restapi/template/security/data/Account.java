package com.restapi.template.security.data;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 계정 엔터티.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@MappedSuperclass
public class Account {
  /**
   * pk.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /**
   * 사용자 ID.
   */
  @Column(unique = true)
  private String userId;

  /**
   * 비밀번호.
   */
  private String password;

  /**
   * 사용자 이름.
   */
  private String name;

  /**
   * 게정 상태.
   */
  private UserStatus state;

  /**
   * Refresh Token.
   */
  private String refreshToken;

  /**
   * 사용자 권한.
   */
  @ElementCollection(fetch = FetchType.LAZY)
  private List<UserRole> roles;

  /**
   * Refresh Token 갱신.
   *
   * @param refreshToken RefreshToken
   */
  public void updateRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  /**
   * 계정.
   *
   * @param userId       사용자 ID
   * @param password     비밀번호
   * @param name         사용자 이름
   * @param state        사용자 상태
   * @param roles        사용자 권한
   * @param refreshToken refresh 토큰
   */
  public Account(String userId, String password, String name, UserStatus state,
                 List<UserRole> roles, String refreshToken) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.state = state;
    this.refreshToken = refreshToken;
    this.roles = roles;
  }
}
