package com.multimodule.msa.api.auth.service;

import com.multimodule.msa.api.auth.model.Account;
import com.multimodule.msa.api.auth.exception.CantSignInException;
import com.multimodule.msa.api.auth.exception.IdAlreadyExistsException;
import com.multimodule.msa.api.auth.request.RefreshRequest;
import com.multimodule.msa.api.auth.response.RefreshResponse;
import com.multimodule.msa.api.auth.response.SignInResponse;
import com.multimodule.msa.api.user.model.entity.Users;
import com.multimodule.msa.api.user.model.repository.UsersRepository;
import com.multimodule.msa.authentication.JwtTokenProvider;
import com.multimodule.msa.authentication.UserRole;
import com.multimodule.msa.authentication.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

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

    @Transactional(readOnly = true)
    public void idCheck(String id) {
        if (this.usersRepository.findByUserIdAndStateIsNot(id, UserStatus.WITHDRAWAL).isPresent())
            throw new IdAlreadyExistsException(id);
    }

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
