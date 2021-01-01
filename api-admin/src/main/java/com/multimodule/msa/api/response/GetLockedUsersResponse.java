package com.multimodule.msa.api.response;

import com.multimodule.msa.dto.UserStateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetLockedUsersResponse {
    private List<UserStateDto> lockedUsers;

    public GetLockedUsersResponse(List<UserStateDto> lockedUsers) {
        this.lockedUsers = lockedUsers;
    }
}
