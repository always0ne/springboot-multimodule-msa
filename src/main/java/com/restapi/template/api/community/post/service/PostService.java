package com.restapi.template.api.community.post.service;

import com.restapi.template.api.common.exception.ThisIsNotYoursException;
import com.restapi.template.api.community.post.data.Post;
import com.restapi.template.api.community.post.data.PostRepository;
import com.restapi.template.api.community.post.dto.PostDetailDto;
import com.restapi.template.api.community.post.dto.PostsDto;
import com.restapi.template.api.community.post.exception.PostNotFoundException;
import com.restapi.template.api.community.post.request.ModifyPostRequest;
import com.restapi.template.api.user.data.Users;
import com.restapi.template.api.user.data.UsersRepository;
import com.restapi.template.api.user.exception.InvalidUserException;
import com.restapi.template.security.data.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 서비스.
 *
 * @author always0ne
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final UsersRepository usersRepository;

  /**
   * 모든 게시글 조회(Paged).
   *
   * @param pageable 페이지 정보
   * @return 페이징 처리가 된 게시글
   */
  @Transactional
  public Page<PostsDto> getPosts(Pageable pageable) {
    return this.postRepository.findAllProjectedBy(pageable);
  }

  /**
   * 게시글 작성.
   *
   * @param requestUserId     요청한 사용자 ID
   * @param modifyPostRequest 게시글 정보
   * @return 게시글 ID
   */
  @Transactional
  public Long savePost(String requestUserId, ModifyPostRequest modifyPostRequest) {
    return this.postRepository.save(
        new Post(
            null,
            usersRepository.findByUserIdAndState(requestUserId, UserStatus.NORMAL, Users.class)
                .orElseThrow(InvalidUserException::new),
            modifyPostRequest.getTitle(),
            modifyPostRequest.getBody()
        )).getPostId();
  }

  /**
   * 게시글 조회.
   *
   * @param postId 게시글 Id
   * @return 게시글
   * @throws PostNotFoundException 존재하지 않는 게시글입니다.
   */
  @Transactional
  public PostDetailDto getPost(Long postId) {
    Post post = this.postRepository.findByPostId(postId)
        .orElseThrow(PostNotFoundException::new);
    post.increaseViews();

    return new PostDetailDto(post);
  }

  /**
   * 게시글 수정.
   *
   * @param postId            게시글 ID
   * @param requestUserId     요청한 사용자 ID
   * @param modifyPostRequest 게시글 정보
   */
  @Transactional
  public void updatePost(Long postId, String requestUserId, ModifyPostRequest modifyPostRequest) {
    getMyPost(postId, requestUserId)
        .updatePost(modifyPostRequest.getTitle(), modifyPostRequest.getBody());
  }

  /**
   * 게시글 삭제.
   *
   * @param postId        게시글 ID
   * @param requestUserId 요청한 사용자 ID
   */
  @Transactional
  public void deletePost(Long postId, String requestUserId) {
    this.postRepository.deleteById(getMyPost(postId, requestUserId).getPostId());
  }

  /**
   * 내 게시글 가져오기.
   *
   * @param postId        게시글 ID
   * @param requestUserId 요청한 사용자 ID
   * @return 게시글 엔터티
   * @throws ThisIsNotYoursException 수정권한이 없습니다.
   */
  private Post getMyPost(Long postId, String requestUserId) {
    return postRepository.findByPostIdAndAuthor_UserId(postId, requestUserId)
        .orElseThrow(ThisIsNotYoursException::new);
  }
}
