package com.multimodule.msa.model;

import com.multimodule.msa.authentication.UserRole;
import com.multimodule.msa.authentication.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@MappedSuperclass
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String userId;
    private String password;
    private String name;
    private UserStatus state;
    private String refreshToken;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<UserRole> roles;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Account(String userId, String password, String name, UserStatus state, List<UserRole> roles, String refreshToken) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.state = state;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }
}
