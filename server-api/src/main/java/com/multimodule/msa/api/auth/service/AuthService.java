package com.multimodule.msa.api.auth.service;

import com.multimodule.msa.api.auth.data.Account;
import com.multimodule.msa.api.auth.exception.CantSignInException;
import com.multimodule.msa.api.auth.exception.IdAlreadyExistsException;
import com.multimodule.msa.api.auth.request.RefreshRequest;
import com.multimodule.msa.api.auth.response.RefreshResponse;
import com.multimodule.msa.api.auth.response.SignInResponse;
import com.multimodule.msa.api.user.data.Users;
import com.multimodule.msa.api.user.data.UsersRepository;
import com.multimodule.msa.authentication.JwtTokenProvider;
import com.multimodule.msa.authentication.UserRole;
import com.multimodule.msa.authentication.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * 회원 인증 서비스
 *
 * @author always0ne
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 인증토큰 발급받기
     * 새로 로그인 할 때마다 RefreshToken 이 갱신된다.
     *
     * @param id       사용자 ID
     * @param password 사용자 비밀번호
     * @return accessToken
     * @throws CantSignInException 회원가입이 되어있지 않거나 잠긴 계정입니다.
     */
    @Transactional
    public SignInResponse signIn(String id, String password) {
        Account account = this.usersRepository.findByUserIdAndState(id, UserStatus.NORMAL, Account.class)
                .orElseThrow(() -> new CantSignInException(id));
        if (!passwordEncoder.matches(password, account.getPassword()))
            throw new CantSignInException(id);
        account.updateRefreshToken(jwtTokenProvider.createRefreshToken(account.getUserId(), account.getRoles()));

        return SignInResponse.builder()
                .accessToken(jwtTokenProvider.createAccessToken(account.getUserId(), account.getRoles()))
                .refreshToken(account.getRefreshToken())
                .build();
    }

    /**
     * 회원 가입 하기
     * 회원가입과 동시에 인증토큰 발급
     *
     * @param id       사용자 ID
     * @param password 사용자 비밀번호
     * @param name     사용자 이름
     * @return accessToken
     */
    @Transactional
    public SignInResponse signUp(String id, String password, String name) {
        Account account = this.usersRepository.save(
                new Users(
                        id,
                        passwordEncoder.encode(password),
                        name,
                        UserStatus.NORMAL,
                        Collections.singletonList(UserRole.ROLE_USER),
                        jwtTokenProvider.createRefreshToken(id, Collections.singletonList(UserRole.ROLE_USER))
                ));

        return SignInResponse.builder()
                .accessToken(jwtTokenProvider.createAccessToken(account.getUserId(), account.getRoles()))
                .refreshToken(account.getRefreshToken())
                .build();
    }

    /**
     * 중복 아이디 체크
     *
     * @param id 사용자 ID
     * @throws IdAlreadyExistsException 이미 사용중인 아이디입니다.
     */
    @Transactional(readOnly = true)
    public void idCheck(String id) {
        if (this.usersRepository.findByUserIdAndStateIsNot(id, UserStatus.WITHDRAWAL).isPresent())
            throw new IdAlreadyExistsException(id);
    }

    /**
     * RefreshToken 으로 AccessToken 재발급
     *
     * @param refreshRequest AccessToken, RefreshToken
     * @return AccessToken
     */
    @Transactional
    public RefreshResponse refreshAccessToken(RefreshRequest refreshRequest) {
        String refreshId = jwtTokenProvider.getUserId(jwtTokenProvider.getClaimsFromToken(refreshRequest.getRefreshToken()));
        Account account = usersRepository.findByUserIdAndStateAndRefreshToken(refreshId, UserStatus.NORMAL, refreshRequest.getRefreshToken())
                .orElseThrow(() -> new CantSignInException(refreshId));

        return RefreshResponse.builder()
                .accessToken(jwtTokenProvider.createAccessToken(account.getUserId(), account.getRoles()))
                .build();
    }
}
