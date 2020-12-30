package com.restapi.template.api.user.data;

import com.restapi.template.security.data.Account;
import com.restapi.template.security.data.UserRole;
import com.restapi.template.security.data.UserStatus;
import java.util.List;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Users extends Account {

  public Users(String userId, String password, String name, UserStatus state,
               List<UserRole> roles, String refreshToken) {
    super(userId, password, name, state, roles, refreshToken);
  }
}
