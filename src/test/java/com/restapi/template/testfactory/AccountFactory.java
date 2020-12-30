package com.restapi.template.testfactory;

import com.restapi.template.api.user.data.Users;
import com.restapi.template.api.user.data.UsersRepository;
import com.restapi.template.security.data.UserStatus;
import com.restapi.template.security.response.SignInResponse;
import com.restapi.template.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountFactory {

  @Autowired
  private AuthService authService;
  @Autowired
  protected UsersRepository usersRepository;

  /**
   * user 생성.
   *
   * @param index index
   * @return SignInResponse
   */
  @Transactional
  public SignInResponse generateUser(int index) {
    return authService.signUp(
        "TestUser" + index,
        "password",
        "테스트 유저 " + index
    );
  }

  /**
   * user 생성후 User 반환.
   *
   * @param index index
   * @return SignInResponse
   */
  @Transactional
  public Users generateUserAndGetUser(int index) {
    authService.signUp(
        "TestUser" + index,
        "password",
        "테스트 유저 " + index
    );
    return usersRepository.findByUserIdAndState("TestUser" + index, UserStatus.NORMAL, Users.class)
        .get();
  }
}
