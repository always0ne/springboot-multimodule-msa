package com.multimodule.msa.service;

import com.multimodule.msa.authentication.UserStatus;
import com.multimodule.msa.dto.UserStateDto;
import com.multimodule.msa.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    public List<UserStateDto> getLockedUsers() {
        return usersRepository.findAllByState(UserStatus.LOCKED);
    }
}
