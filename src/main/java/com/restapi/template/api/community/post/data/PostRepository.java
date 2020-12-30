package com.restapi.template.api.community.post.data;

import com.restapi.template.api.community.post.dto.PostsDto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 게시글 레포지터리.
 *
 * @author always0ne
 * @version 1.0
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  /**
   * 게시글 아이디로 조회.
   *
   * @param postId 게시글 아이디
   * @return 게시글(Optional)
   */
  Optional<Post> findByPostId(Long postId);

  /**
   * 사용자의 게시글인지 확인하며 포스트 조회.
   *
   * @param postId 게시글 아이디
   * @param userId 작성자 ID
   * @return 게시글(Optional)
   */
  Optional<Post> findByPostIdAndAuthor_UserId(Long postId, String userId);

  /**
   * 모든 게시글 조회(Pagenation).
   *
   * @param pageable 페이지 정보
   * @return 모든 게시글(Page)
   */
  @Query(value = "SELECT new com.restapi.template.api.community.post.dto.PostsDto("
      + "postId, title, author.userId, views, commentNum, modifiedDate)"
      + " FROM Post",
      countQuery = "SELECT count(postId) FROM Post")
  Page<PostsDto> findAllProjectedBy(Pageable pageable);
}
