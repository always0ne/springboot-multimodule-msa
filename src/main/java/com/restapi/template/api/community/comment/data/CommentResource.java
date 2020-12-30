package com.restapi.template.api.community.comment.data;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.restapi.template.api.community.dto.CommentDto;
import com.restapi.template.api.community.post.controller.PostController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

/**
 * 댓글 정보.
 *
 * @author always0ne
 * @version 1.0
 */
public class CommentResource extends EntityModel<CommentDto> {
  /**
   * 댓글 Resource(for Hateoas).
   *
   * @param comment 댓글
   * @param links   링크
   */
  public CommentResource(Comment comment, Link... links) {
    super(new CommentDto(comment), links);
    add(linkTo(PostController.class).slash(comment.getCommentId()).withSelfRel());
    add(linkTo(PostController.class).slash(comment.getCommentId()).withRel("updateComment"));
    add(linkTo(PostController.class).slash(comment.getCommentId()).withRel("deleteComment"));
  }
}
