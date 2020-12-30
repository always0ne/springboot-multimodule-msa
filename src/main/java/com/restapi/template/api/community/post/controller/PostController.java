package com.restapi.template.api.community.post.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.restapi.template.api.common.DocsController;
import com.restapi.template.api.common.response.LinksResponse;
import com.restapi.template.api.community.post.dto.PostDetailDto;
import com.restapi.template.api.community.post.dto.PostsDto;
import com.restapi.template.api.community.post.request.ModifyPostRequest;
import com.restapi.template.api.community.post.response.PostResponse;
import com.restapi.template.api.community.post.response.PostsResponse;
import com.restapi.template.api.community.post.service.PostService;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 게시글 컨트롤러.
 *
 * @author always0ne
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/board/posts", produces = MediaTypes.HAL_JSON_VALUE)
public class PostController {

  private final PostService postService;

  /**
   * 모든 게시글 조회(Paged).
   * body랑 comments가 조회 안되게 수정필요
   *
   * @param pageable  페이지 정보
   * @param assembler 어셈블러
   * @return 페이징 처리된 게시글
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public PagedModel<PostsResponse> getPosts(
      Pageable pageable,
      PagedResourcesAssembler<PostsDto> assembler
  ) {
    Page<PostsDto> posts = this.postService.getPosts(pageable);
    PagedModel<PostsResponse> postsResponses =
        assembler.toModel(posts, postsDto -> new PostsResponse(postsDto));
    postsResponses.add(linkTo(DocsController.class).slash("#getPosts").withRel("profile"));

    return postsResponses;
  }

  /**
   * 게시글 작성.
   *
   * @param modifyPostRequest 게시글 정보
   * @param response          헤더 설정을 위한 response 객체
   * @return self 링크, API Docs 링크
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public LinksResponse savePost(
      @RequestBody ModifyPostRequest modifyPostRequest,
      HttpServletResponse response

  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    Long postId = this.postService.savePost(requestUserId, modifyPostRequest);

    response.setHeader("Location", linkTo(PostController.class).slash(postId).toUri().toString());
    return new LinksResponse(
        linkTo(PostController.class).slash(postId).withSelfRel(),
        linkTo(DocsController.class).slash("#sendPost").withRel("profile")
    );
  }

  /**
   * 게시글 조회.
   *
   * @param postId 게시글 Id
   * @return 게시글
   */
  @GetMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostResponse getPost(
      @PathVariable Long postId
  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    PostDetailDto post = this.postService.getPost(postId);
    PostResponse postResponse = new PostResponse(post, postId);
    if (requestUserId.equals(post.getWriterId())) {
      postResponse.add(linkTo(PostController.class).slash(postId).withRel("updatePost"));
      postResponse.add(linkTo(PostController.class).slash(postId).withRel("deletePost"));
    }
    return postResponse;
  }

  /**
   * 게시글 수정.
   *
   * @param postId            게시글 Id
   * @param modifyPostRequest 게시글 정보
   * @return self 링크, API Docs 링크
   */
  @PutMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public LinksResponse updatePost(
      @PathVariable Long postId,
      @RequestBody ModifyPostRequest modifyPostRequest
  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    this.postService.updatePost(postId, requestUserId, modifyPostRequest);

    return new LinksResponse(
        linkTo(PostController.class).slash(postId).withSelfRel(),
        linkTo(DocsController.class).slash("#updatePost").withRel("profile")
    );
  }

  /**
   * 게시글 삭제.
   *
   * @param postId 게시글 ID
   * @return self 링크, API Docs 링크
   */
  @DeleteMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public LinksResponse deletePost(
      @PathVariable Long postId
  ) {
    String requestUserId = SecurityContextHolder.getContext().getAuthentication().getName();
    this.postService.deletePost(postId, requestUserId);

    return new LinksResponse(
        linkTo(PostController.class).slash(postId).withSelfRel(),
        linkTo(DocsController.class).slash("#deletePost").withRel("profile")
    );
  }
}
