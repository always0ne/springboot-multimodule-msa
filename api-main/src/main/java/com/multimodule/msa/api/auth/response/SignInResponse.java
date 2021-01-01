package com.multimodule.msa.api.auth.response;

import com.multimodule.msa.dto.JwtTokensDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponse {

    private String accessToken;
    private String refreshToken;

    public SignInResponse(JwtTokensDto jwtTokensDto){
        this.accessToken = jwtTokensDto.getAccessToken();
        this.refreshToken = jwtTokensDto.getRefreshToken();
    }
}
