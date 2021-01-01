package com.multimodule.msa.dto;

import com.multimodule.msa.authentication.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserStateDto {

    private String userId;
    private String name;
    private UserStatus state;
}
