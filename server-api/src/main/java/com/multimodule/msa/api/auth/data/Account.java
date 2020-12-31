package com.multimodule.msa.api.auth.data;

import com.multimodule.msa.authentication.UserRole;
import com.multimodule.msa.authentication.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * 계정 엔터티
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@MappedSuperclass
public class Account {
    /**
     * pk
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 사용자 ID
     */
    @Column(unique = true)
    private String userId;

    /**
     * 비밀번호
     */
    private String password;

    /**
     * 사용자 이름
     */
    private String name;

    /**
     * 게정 상태
     */
    private UserStatus state;

    /**
     * Refresh Token
     */
    private String refreshToken;

    /**
     * 사용자 권한
     */
    @ElementCollection(fetch = FetchType.LAZY)
    private List<UserRole> roles;

    /**
     * Refresh Token 갱신
     *
     * @param refreshToken RefreshToken
     */
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
