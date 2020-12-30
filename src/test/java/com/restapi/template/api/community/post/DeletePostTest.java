package com.restapi.template.api.community.post;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.restapi.template.api.common.BaseControllerTest;
import com.restapi.template.api.community.post.data.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("게시글 삭제 테스트")
class DeletePostTest extends BaseControllerTest {

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("포스트 삭제(성공)")
  void deletePostSuccess() throws Exception {
    Post post = this.postFactory.generatePost(1);
    this.mockMvc
        .perform(RestDocumentationRequestBuilders.delete("/board/posts/{postId}", post.getPostId()))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("deletePost",
            pathParameters(
                parameterWithName("postId").description("게시글 아이디")
            )));
  }
}
