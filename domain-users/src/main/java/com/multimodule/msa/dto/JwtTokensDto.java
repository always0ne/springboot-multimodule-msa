package com.multimodule.msa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtTokensDto {

    private String accessToken;
    private String refreshToken;
}
