package com.multimodule.msa.api.auth.controller;

import com.multimodule.msa.api.auth.request.RefreshRequest;
import com.multimodule.msa.api.auth.request.SignInRequest;
import com.multimodule.msa.api.auth.request.SignUpRequest;
import com.multimodule.msa.api.auth.response.RefreshResponse;
import com.multimodule.msa.api.auth.response.SignInResponse;
import com.multimodule.msa.dto.JwtTokensDto;
import com.multimodule.msa.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = MediaTypes.HAL_JSON_VALUE)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponse signIn(
            @RequestBody SignInRequest signInRequest
    ) {
        JwtTokensDto jwtTokensDto = this.authService.signIn(signInRequest.getId(), signInRequest.getPassword());
        return new SignInResponse(jwtTokensDto);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public SignInResponse signUp(
            @RequestBody SignUpRequest signUpRequest
    ) {
        JwtTokensDto jwtTokensDto = this.authService.signUp(signUpRequest.getId(), signUpRequest.getPassword(), signUpRequest.getName());
        return new SignInResponse(jwtTokensDto);
    }

    @GetMapping("/checkid/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String idCheck(
            @PathVariable String userId
    ) {
        this.authService.checkId(userId);
        return "사용가능한 아이디입니다.";
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public RefreshResponse getNewAccessToken(
            @RequestBody RefreshRequest refreshRequest
    ) {
        JwtTokensDto jwtTokensDto =  this.authService.refreshAccessToken(refreshRequest.getRefreshToken());
        return new RefreshResponse(jwtTokensDto);
    }

}
