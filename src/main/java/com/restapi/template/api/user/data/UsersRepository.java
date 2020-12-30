package com.restapi.template.api.user.data;

import com.restapi.template.api.user.data.dto.UserIdDto;
import com.restapi.template.security.data.Account;
import com.restapi.template.security.data.UserStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 계정 레포지터리.
 *
 * @author always0ne
 * @version 1.0
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
  /**
   * 계정 ID와 상태로 조회.
   *
   * @param userId 사용자 ID
   * @param state  조회할 상태
   * @param dataType projection할 데이터 타입
   * @return 계정(Optional)
   */
  <T> Optional<T> findByUserIdAndState(String userId, UserStatus state, Class<T> dataType);

  /**
   * 계정 ID와 제외된 상태로 조회.
   *
   * @param userId 사용자 ID
   * @param state  제외할 상태
   * @return 계정(Optional)
   */
  Optional<UserIdDto> findByUserIdAndStateIsNot(String userId, UserStatus state);

  /**
   * 계정 ID와 상태로 조회.
   *
   * @param userId       사용자 ID
   * @param state        조회할 상태
   * @param refreshToken RefreshToken
   * @return 계정(Optional)
   */
  Optional<Account> findByUserIdAndStateAndRefreshToken(String userId, UserStatus state,
                                                        String refreshToken);
}
