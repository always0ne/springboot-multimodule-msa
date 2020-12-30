package com.restapi.template.api.user.service;

import com.restapi.template.api.user.data.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 사용자 서비스.
 *
 * @author always0ne
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

  private final UsersRepository usersRepository;

}
