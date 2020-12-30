package com.restapi.template.api.community.post;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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

@DisplayName("게시글 조회 테스트")
class GetPostTest extends BaseControllerTest {

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("포스트 목록 조회(성공)")
  void getPostsSuccess() throws Exception {
    this.postFactory.generatePost(1);
    this.postFactory.generatePost(2);
    this.postFactory.generatePost(3);
    this.postFactory.generatePost(4);
    this.postFactory.generatePost(5);
    this.postFactory.generatePost(6);
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/board/posts")
        .param("page", "1")
        .param("size", "2")
        .param("sort", "title,DESC"))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("getPosts",
            responseFields(
                fieldWithPath("_embedded.postList[].postId").description("게시글 아이디"),
                fieldWithPath("_embedded.postList[].title").description("게시글 제목"),
                fieldWithPath("_embedded.postList[].writerId").description("작성자 아이디"),
                fieldWithPath("_embedded.postList[].views").description("조회수"),
                fieldWithPath("_embedded.postList[].commentNum").description("댓글수"),
                fieldWithPath("_embedded.postList[].modifiedDate").description("수정일"),
                fieldWithPath("_embedded.postList[]._links.self.href").description("게시글 데이터 링크"),
                fieldWithPath("_links.self.href").description("Self 링크"),
                fieldWithPath("_links.profile.href").description("해당 링크의 Api Docs"),
                fieldWithPath("_links.first.href").description("첫번째 목록"),
                fieldWithPath("_links.prev.href").description("이전 목록"),
                fieldWithPath("_links.next.href").description("다음 목록"),
                fieldWithPath("_links.last.href").description("마지막 목록"),
                fieldWithPath("page.size").description("한 페이지에 조회되는 게시글수"),
                fieldWithPath("page.totalElements").description("총 게시글수"),
                fieldWithPath("page.totalPages").description("총 페이지"),
                fieldWithPath("page.number").description("현재 페이지")
            )
        ));
  }

  @Test
  @DisplayName("포스트 조회(성공)")
  void getPostSuccess() throws Exception {
    Post post = this.postFactory.generatePost(1);
    this.commentFactory.addComment(post, 2);
    this.commentFactory.addComment(post, 3);
    this.mockMvc
        .perform(RestDocumentationRequestBuilders.get("/board/posts/{postId}", post.getPostId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("title").value("게시글1"))
        .andExpect(jsonPath("writerId").value("TestUser1"))
        .andExpect(jsonPath("body").value("게시글 본문입니다."))
        .andDo(print())
        .andDo(document("getPost",
            pathParameters(
                parameterWithName("postId").description("게시글 아이디")
            ),
            responseFields(
                fieldWithPath("title").description("게시글 제목"),
                fieldWithPath("writerId").description("작성자 아이디"),
                fieldWithPath("body").description("게시글 내용"),
                fieldWithPath("views").description("조회수"),
                fieldWithPath("createdDate").description("작성일"),
                fieldWithPath("modifiedDate").description("수정일"),
                fieldWithPath("comments[].commentId").description("댓글 Id"),
                fieldWithPath("comments[].commenterId").description("댓글 작성자 Id"),
                fieldWithPath("comments[].message").description("댓글"),
                fieldWithPath("comments[].createdDate").description("댓글 작성시간"),
                fieldWithPath("comments[].modifiedDate").description("댓글 수정시간"),
                fieldWithPath("comments[]._links.self.href").description("댓글 Self 링크"),
                fieldWithPath("comments[]._links.updateComment.href").description("댓글 수정하기"),
                fieldWithPath("comments[]._links.deleteComment.href").description("댓글 삭제하기"),
                fieldWithPath("_links.self.href").description("Self 링크"),
                fieldWithPath("_links.sendComment.href").description("게시글에 댓글달기"),
                fieldWithPath("_links.profile.href").description("해당 링크의 Api Docs")
            ),
            links(
                linkWithRel("self").description("Self 링크"),
                linkWithRel("sendComment").description("게시글에 댓글달기"),
                linkWithRel("profile").description("해당 링크의 Api Docs")
            )
        ));

  }

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("나의 포스트 조회(성공)")
  void getMyPostSuccess() throws Exception {
    Post post = this.postFactory.generatePost(1);
    this.commentFactory.addComment(post, 2);
    this.commentFactory.addComment(post, 3);
    this.mockMvc
        .perform(RestDocumentationRequestBuilders.get("/board/posts/{postId}", post.getPostId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("title").value("게시글1"))
        .andExpect(jsonPath("writerId").value("TestUser1"))
        .andExpect(jsonPath("body").value("게시글 본문입니다."))
        .andDo(print())
        .andDo(document("getMyPost",
            pathParameters(
                parameterWithName("postId").description("게시글 아이디")
            ),
            responseFields(
                fieldWithPath("title").description("게시글 제목"),
                fieldWithPath("writerId").description("작성자 아이디"),
                fieldWithPath("body").description("게시글 내용"),
                fieldWithPath("views").description("조회수"),
                fieldWithPath("createdDate").description("작성일"),
                fieldWithPath("modifiedDate").description("수정일"),
                fieldWithPath("comments[].commentId").description("댓글 Id"),
                fieldWithPath("comments[].commenterId").description("댓글 작성자 Id"),
                fieldWithPath("comments[].message").description("댓글"),
                fieldWithPath("comments[].createdDate").description("댓글 작성시간"),
                fieldWithPath("comments[].modifiedDate").description("댓글 수정시간"),
                fieldWithPath("comments[]._links.self.href").description("댓글 Self 링크"),
                fieldWithPath("comments[]._links.updateComment.href").description("댓글 수정하기"),
                fieldWithPath("comments[]._links.deleteComment.href").description("댓글 삭제하기"),
                fieldWithPath("_links.self.href").description("Self 링크"),
                fieldWithPath("_links.updatePost.href").description("게시글 수정하기"),
                fieldWithPath("_links.deletePost.href").description("게시글 삭제하기"),
                fieldWithPath("_links.sendComment.href").description("게시글에 댓글달기"),
                fieldWithPath("_links.profile.href").description("해당 링크의 Api Docs")
            ),
            links(
                linkWithRel("self").description("Self 링크"),
                linkWithRel("updatePost").description("게시글 수정하기"),
                linkWithRel("deletePost").description("게시글 삭제하기"),
                linkWithRel("sendComment").description("게시글에 댓글달기"),
                linkWithRel("profile").description("해당 링크의 Api Docs")
            )
        ));

  }

  @Test
  @WithMockUser("TestUser1")
  @DisplayName("포스트 조회(게시글이 없을때)")
  void getPostNoPostFailBecauseNotFound() throws Exception {
    this.mockMvc.perform(RestDocumentationRequestBuilders.get("/board/posts/{postId}", 1))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("error").value("1101"))
        .andDo(print());
  }
}
