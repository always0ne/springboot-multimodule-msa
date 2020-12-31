package com.multimodule.msa.api.user.model.entity;

import com.multimodule.msa.api.auth.model.Account;
import com.multimodule.msa.authentication.UserRole;
import com.multimodule.msa.authentication.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Users extends Account {

    public Users(String userId, String password, String name, UserStatus state, List<UserRole> roles, String refreshToken) {
        super(userId, password, name, state, roles, refreshToken);
    }
}