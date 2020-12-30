package com.restapi.template.api.community.post;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.restapi.template.api.common.BaseControllerTest;
import com.restapi.template.api.community.post.request.ModifyPostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("게시글 삽입 테스트")
class InsertPostTest extends BaseControllerTest {

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("포스트 저장(성공)")
  void insertPostSuccess() throws Exception {
    String accessToken = accountFactory.generateUser(1).getAccessToken();
    ModifyPostRequest modifyPostRequest = ModifyPostRequest.builder()
        .title("포스트 제목")
        .body("포스트 입력 테스트입니다.")
        .build();
    this.mockMvc.perform(RestDocumentationRequestBuilders.post("/board/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + accessToken)
        .content(this.objectMapper.writeValueAsString(modifyPostRequest)))
        .andExpect(status().isCreated())
        .andDo(print())
        .andDo(document("sendPost",
            requestFields(
                fieldWithPath("title").description("게시글 제목"),
                fieldWithPath("body").description("게시글 내용")
            )));
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/board/posts/{postId}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("title").value("포스트 제목"))
        .andExpect(jsonPath("body").value("포스트 입력 테스트입니다."));
  }
}
