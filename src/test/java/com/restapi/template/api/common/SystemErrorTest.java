package com.restapi.template.api.common;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.restapi.template.api.community.comment.request.AddCommentRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("시스템 오류 테스트")
class SystemErrorTest extends BaseControllerTest {

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("맞지 않는 RequestBody가 왔을 때")
  void systemFailBecauseWrongRequest() throws Exception {
    AddCommentRequest addCommentRequest = AddCommentRequest.builder().message("wrong").build();
    this.mockMvc.perform(RestDocumentationRequestBuilders.post("/board/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(addCommentRequest)))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andDo(document("2001"))
    ;
  }

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("해석할 수 없는 RequestBody가 왔을 때")
  void systemFailBecauseCantParse() throws Exception {
    this.mockMvc.perform(RestDocumentationRequestBuilders.post("/board/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content("Can't Parse Value Like this"))
        .andExpect(status().isBadRequest())
        .andDo(print())
    ;
  }
}
