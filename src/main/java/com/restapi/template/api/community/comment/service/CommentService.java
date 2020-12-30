package com.restapi.template.api.community.comment.service;

import com.restapi.template.api.common.exception.ThisIsNotYoursException;
import com.restapi.template.api.community.comment.data.Comment;
import com.restapi.template.api.community.comment.data.CommentRepository;
import com.restapi.template.api.community.comment.request.AddCommentRequest;
import com.restapi.template.api.community.comment.request.UpdateCommentRequest;
import com.restapi.template.api.community.post.data.Post;
import com.restapi.template.api.community.post.data.PostRepository;
import com.restapi.template.api.community.post.exception.PostNotFoundException;
import com.restapi.template.api.user.data.Users;
import com.restapi.template.api.user.data.UsersRepository;
import com.restapi.template.api.user.exception.InvalidUserException;
import com.restapi.template.security.data.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 서비스.
 *
 * @author always0ne
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final UsersRepository usersRepository;

  /**
   * 댓글 입력.
   *
   * @param postId            게시글 ID
   * @param requestUserId     요청한 사용자 ID
   * @param addCommentRequest 댓글 작성 요청
   * @throws PostNotFoundException 존재하지 않는 게시글입니다.
   */
  @Transactional
  public void saveComment(Long postId, String requestUserId, AddCommentRequest addCommentRequest) {
    Post post = this.postRepository.findByPostId(postId)
        .orElseThrow(PostNotFoundException::new);
    Comment comment = this.commentRepository.save(
        new Comment(
            usersRepository.findByUserIdAndState(requestUserId, UserStatus.NORMAL, Users.class)
                .orElseThrow(InvalidUserException::new),
            addCommentRequest.getMessage())
    );
    post.addComment(comment);
  }

  /**
   * 댓글 수정.
   *
   * @param postId               게시글 ID
   * @param commentId            댓글 ID
   * @param requestUserId        요청한 사용자 ID
   * @param updateCommentRequest 댓글 수정 요청
   * @throws PostNotFoundException   존재하지 않는 게시글입니다.
   * @throws ThisIsNotYoursException 수정권한이 없습니다.
   */
  @Transactional
  public void updateComment(Long postId, Long commentId, String requestUserId,
                            UpdateCommentRequest updateCommentRequest) {
    Post post = this.postRepository.findByPostId(postId)
        .orElseThrow(PostNotFoundException::new);

    post.updateComment(
        getMyComment(commentId, requestUserId),
        updateCommentRequest.getMessage());
  }

  /**
   * 댓글 삭제.
   *
   * @param postId        게시글 ID
   * @param commentId     댓글 ID
   * @param requestUserId 요청한 사용자 ID
   * @throws PostNotFoundException   존재하지 않는 게시글입니다.
   * @throws ThisIsNotYoursException 수정권한이 없습니다.
   */
  @Transactional
  public void deleteComment(Long postId, Long commentId, String requestUserId) {
    Post post = this.postRepository.findByPostId(postId)
        .orElseThrow(PostNotFoundException::new);
    Comment comment = getMyComment(commentId, requestUserId);
    post.deleteComment(comment);
    this.commentRepository.delete(comment);
  }

  /**
   * 내 댓글 가져오기.
   *
   * @param commentId     댓글 ID
   * @param requestUserId 요청한 사용자 ID
   * @return 댓글 엔터티
   * @throws ThisIsNotYoursException 수정권한이 없습니다.
   */
  public Comment getMyComment(Long commentId, String requestUserId) {
    return commentRepository.findByAuthor_UserIdAndCommentId(requestUserId, commentId)
        .orElseThrow(ThisIsNotYoursException::new);
  }
}
