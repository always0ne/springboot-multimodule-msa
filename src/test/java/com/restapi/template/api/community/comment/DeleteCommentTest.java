package com.restapi.template.api.community.comment;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.restapi.template.api.common.BaseControllerTest;
import com.restapi.template.api.community.post.data.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

@DisplayName("댓글 삭제 테스트")
class DeleteCommentTest extends BaseControllerTest {

  @Test
  @WithMockUser("TestUser2")
  @DisplayName("댓글 지우기(성공)")
  void deleteCommentSuccess() throws Exception {
    Post post = this.postFactory.generatePost(1);
    long commentId = this.commentFactory.addComment(post, 2);
    this.mockMvc.perform(RestDocumentationRequestBuilders
        .delete("/board/posts/{postId}/{commentId}", post.getPostId(), commentId))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("deleteComment",
            pathParameters(
                parameterWithName("postId").description("게시글 번호"),
                parameterWithName("commentId").description("댓글 번호")
            )));
  }

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("댓글 지우기(포스트가 없을때)")
  void deleteCommentFailBecausePostNotExist() throws Exception {
    this.mockMvc
        .perform(RestDocumentationRequestBuilders
            .delete("/board/posts/{postId}/{commentId}", 1, 1))
        .andExpect(status().isNotFound())
        .andDo(print())
        .andExpect(jsonPath("error").value("1101"))
        .andDo(document("1101"))
    ;
  }

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("댓글 지우기(댓글이 없을때)")
  void deleteCommentFailBecauseCommentNotExist() throws Exception {
    Post post = this.postFactory.generatePost(1);
    this.mockMvc.perform(RestDocumentationRequestBuilders
        .delete("/board/posts/{postId}/{commentId}", post.getPostId(), 1))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andExpect(jsonPath("error").value("1001"))
        .andDo(document("1001"))
    ;
  }

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("댓글 지우기(내 댓글이 아닐때)")
  void deleteCommentFailBecauseCommentIsNotMine() throws Exception {
    Post post = this.postFactory.generatePost(1);
    long commentId = this.commentFactory.addComment(post, 2);
    this.mockMvc.perform(RestDocumentationRequestBuilders
        .delete("/board/posts/{postId}/{commentId}", post.getPostId(), commentId))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andExpect(jsonPath("error").value("1001"))
    ;
  }
}