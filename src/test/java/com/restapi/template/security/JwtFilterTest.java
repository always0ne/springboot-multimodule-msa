package com.restapi.template.security;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.restapi.template.api.common.BaseControllerTest;
import com.restapi.template.security.data.UserRole;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

@DisplayName("JWT 필터 테스트")
class JwtFilterTest extends BaseControllerTest {

  @Autowired
  JwtTokenProvider jwtTokenProvider;

  @Test
  @DisplayName("Bearer 방식의 인증이 아닐 때")
  void filterFailBecauseNotBearer() throws Exception {
    accountFactory.generateUser(1);
    String token = jwtTokenProvider
        .createAccessToken("TestUser1", Collections.singletonList(UserRole.ROLE_USER));
    this.mockMvc.perform(RestDocumentationRequestBuilders.post("/board/posts")
        .header("Authorization", token))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("error").value("0005"))
        .andDo(document("0005"))
        .andDo(print())
    ;
  }

  @Test
  @DisplayName("만료된 토큰 일 때")
  void filterFailBecauseExpired() throws Exception {
    accountFactory.generateUser(1);
    String token = jwtTokenProvider
        .generateToken("TestUser1", Collections.singletonList(UserRole.ROLE_USER), -10);
    this.mockMvc.perform(RestDocumentationRequestBuilders.post("/board/posts")
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("error").value("0006"))
        .andDo(document("0006"))
        .andDo(print())
    ;
  }
}