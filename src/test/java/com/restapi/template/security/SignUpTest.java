package com.restapi.template.security;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.restapi.template.api.common.BaseControllerTest;
import com.restapi.template.security.request.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

@DisplayName("회원가입 테스트")
class SignUpTest extends BaseControllerTest {

  @Test
  @DisplayName("회원 가입하기(성공)")
  void signUpSuccess() throws Exception {
    SignUpRequest signUpRequest = SignUpRequest.builder()
        .id("TestUser1")
        .name("테스트 유저 1")
        .password("Password")
        .build();

    this.mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(signUpRequest)))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("signup"));
  }

  @Test
  @DisplayName("아이디 중복 조회하기(사용 가능할 때)")
  void idCheckSuccess() throws Exception {
    this.mockMvc
        .perform(RestDocumentationRequestBuilders.get("/auth/checkid/{userId}", "TestUser1"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("idcheck"));
  }

  @Test
  @DisplayName("아이디 중복 조회하기(사용 불가능할 때)")
  void idCheckFailBecauseExists() throws Exception {
    this.accountFactory.generateUser(1);

    this.mockMvc
        .perform(RestDocumentationRequestBuilders.get("/auth/checkid/{userId}", "TestUser1"))
        .andExpect(status().isAccepted())
        .andDo(print())
        .andDo(document("0001"));
  }
}