package com.multimodule.msa.repository;

import com.multimodule.msa.dto.UserStateDto;
import com.multimodule.msa.model.Account;
import com.multimodule.msa.dto.UserIdDto;
import com.multimodule.msa.model.Users;
import com.multimodule.msa.authentication.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    <T> Optional<T> findByUserIdAndState(String userId, UserStatus state, Class<T> Class);

    Optional<UserIdDto> findByUserIdAndStateIsNot(String userId, UserStatus state);

    Optional<Account> findByUserIdAndStateAndRefreshToken(String userId, UserStatus state, String refreshToken);

    List<UserStateDto> findAllByState(UserStatus state);
}
