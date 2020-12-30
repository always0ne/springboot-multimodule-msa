package com.restapi.template.api.community.post.response;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.restapi.template.api.community.post.controller.PostController;
import com.restapi.template.api.community.post.dto.PostsDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

/**
 * 게시글 리스트 응답.
 *
 * @author always0ne
 * @version 1.0
 */
public class PostsResponse extends EntityModel<PostsDto> {
  /**
   * 게시글 리스트 응답.
   * self Link, APIDocs Link
   *
   * @param post  게시글 데이터
   * @param links 추가 링크
   */
  public PostsResponse(PostsDto post, Link... links) {
    super(post, links);
    add(linkTo(PostController.class).slash(post.getPostId()).withSelfRel());
  }
}