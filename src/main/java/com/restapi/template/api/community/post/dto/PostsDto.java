package com.restapi.template.api.community.post.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.server.core.Relation;

/**
 * 게시글 리스트 데이터 전송 객체 내부에서 데이터 이동시 사용된다.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@Builder
@AllArgsConstructor
@Relation(collectionRelation = "postList")
public class PostsDto {
  /**
   * 게시글 ID.
   */
  private Long postId;
  /**
   * 게시글 제목.
   */
  private String title;
  /**
   * 작성자 ID.
   */
  private String writerId;
  /**
   * 조회수.
   */
  private Long views;
  /**
   * 댓글수.
   */
  private Long commentNum;
  /**
   * 수정일.
   */
  private LocalDateTime modifiedDate;
}
