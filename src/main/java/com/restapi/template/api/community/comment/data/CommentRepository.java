package com.restapi.template.api.community.comment.data;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 댓글 레포지터리.
 *
 * @author always0ne
 * @version 1.0
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  /**
   * 사용자의 댓글인지 확인하며 댓글조회.
   *
   * @param userId 작성자 ID
   * @param commentId 댓글 ID
   * @return 댓글(Optional)
   */
  Optional<Comment> findByAuthor_UserIdAndCommentId(String userId, Long commentId);
}
