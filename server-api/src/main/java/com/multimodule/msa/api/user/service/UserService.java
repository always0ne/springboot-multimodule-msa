package com.multimodule.msa.api.user.service;

import com.multimodule.msa.api.user.model.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

}
