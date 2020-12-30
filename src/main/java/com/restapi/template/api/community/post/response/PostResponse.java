package com.restapi.template.api.community.post.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.restapi.template.api.common.DocsController;
import com.restapi.template.api.community.comment.controller.CommentController;
import com.restapi.template.api.community.post.controller.PostController;
import com.restapi.template.api.community.post.dto.PostDetailDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

/**
 * 게시글 상세 응답.
 *
 * @author always0ne
 * @version 1.0
 */
public class PostResponse extends EntityModel<PostDetailDto> {
  /**
   * 게시글 상세 응답.
   * self Link, APIDocs Link
   *
   * @param post   게시글 데이터
   * @param postId 게시글 ID
   * @param links  추가 링크
   */
  public PostResponse(PostDetailDto post, Long postId, Link... links) {
    super(post, links);
    add(linkTo(PostController.class).slash(postId).withSelfRel());
    add(linkTo(CommentController.class, postId).withRel("sendComment"));
    add(linkTo(DocsController.class).slash("#getPost").withRel("profile"));
  }
}
