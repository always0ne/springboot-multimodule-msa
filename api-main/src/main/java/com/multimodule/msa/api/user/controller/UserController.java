package com.multimodule.msa.api.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users", produces = MediaTypes.HAL_JSON_VALUE)
public class UserController {

}
