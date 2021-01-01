package com.multimodule.msa.api.controller;

import com.multimodule.msa.api.response.GetLockedUsersResponse;
import com.multimodule.msa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/users/locked")
    public GetLockedUsersResponse getLockedUsers(){
        return new GetLockedUsersResponse(userService.getLockedUsers());
    }
}
