package com.multimodule.msa.service;

import com.multimodule.msa.authentication.JwtTokenProvider;
import com.multimodule.msa.authentication.UserRole;
import com.multimodule.msa.authentication.UserStatus;
import com.multimodule.msa.dto.JwtTokensDto;
import com.multimodule.msa.exception.CantSignInException;
import com.multimodule.msa.exception.IdAlreadyExistsException;
import com.multimodule.msa.model.Account;
import com.multimodule.msa.model.Users;
import com.multimodule.msa.repository.UsersRepository;
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
    public JwtTokensDto signIn(String id, String password) {
        Account account = this.usersRepository.findByUserIdAndState(id, UserStatus.NORMAL, Account.class)
                .orElseThrow(() -> new CantSignInException(id));
        if (!passwordEncoder.matches(password, account.getPassword()))
            throw new CantSignInException(id);
        account.updateRefreshToken(jwtTokenProvider.createRefreshToken(account.getUserId(), account.getRoles()));

        return new JwtTokensDto(
                jwtTokenProvider.createAccessToken(account.getUserId(), account.getRoles()),
                account.getRefreshToken()
        );
    }

    @Transactional
    public JwtTokensDto signUp(String id, String password, String name) {
        Account account = this.usersRepository.save(
                new Users(
                        id,
                        passwordEncoder.encode(password),
                        name,
                        UserStatus.NORMAL,
                        Collections.singletonList(UserRole.ROLE_USER),
                        jwtTokenProvider.createRefreshToken(id, Collections.singletonList(UserRole.ROLE_USER))
                ));

        return new JwtTokensDto(
                jwtTokenProvider.createAccessToken(account.getUserId(), account.getRoles()),
                account.getRefreshToken()
        );
    }

    @Transactional(readOnly = true)
    public void checkId(String id) {
        if (this.usersRepository.findByUserIdAndStateIsNot(id, UserStatus.WITHDRAWAL).isPresent())
            throw new IdAlreadyExistsException(id);
    }

    @Transactional
    public JwtTokensDto refreshAccessToken(String refreshToken) {
        String refreshId = jwtTokenProvider.getUserId(jwtTokenProvider.getClaimsFromToken(refreshToken));
        Account account = usersRepository.findByUserIdAndStateAndRefreshToken(refreshId, UserStatus.NORMAL, refreshToken)
                .orElseThrow(() -> new CantSignInException(refreshId));

        return new JwtTokensDto(
                jwtTokenProvider.createAccessToken(account.getUserId(), account.getRoles()),
                null
        );
    }
}
