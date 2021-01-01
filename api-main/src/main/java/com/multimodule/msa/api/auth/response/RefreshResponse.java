package com.multimodule.msa.api.auth.response;

import com.multimodule.msa.dto.JwtTokensDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshResponse {
    private String accessToken;

    public RefreshResponse(JwtTokensDto jwtTokensDto){
        this.accessToken = jwtTokensDto.getAccessToken();
    }
}
