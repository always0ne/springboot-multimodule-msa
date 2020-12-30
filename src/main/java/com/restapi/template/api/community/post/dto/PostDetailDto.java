package com.restapi.template.api.community.post.dto;

import com.restapi.template.api.community.comment.data.Comment;
import com.restapi.template.api.community.comment.data.CommentResource;
import com.restapi.template.api.community.post.data.Post;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * 게시글 상세 데이터 전송 객체.
 * 내부에서 데이터 이동시 사용된다.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
public class PostDetailDto {
  /**
   * 게시글 제목.
   */
  private String title;
  /**
   * 작성자 ID.
   */
  private String writerId;
  /**
   * 게시글 본문.
   */
  private String body;
  /**
   * 조회수.
   */
  private Long views;
  /**
   * 작성일.
   */
  private LocalDateTime createdDate;
  /**
   * 수정일.
   */
  private LocalDateTime modifiedDate;

  /**
   * 댓글들.
   */
  private List<CommentResource> comments;

  /**
   * Post to PostDetail Dto.
   *
   * @param post 게시글
   */
  public PostDetailDto(Post post) {
    this.title = post.getTitle();
    this.writerId = post.getAuthor().getUserId();
    this.body = post.getBody();
    this.views = post.getViews();
    this.createdDate = post.getCreatedDate();
    this.modifiedDate = post.getModifiedDate();
    this.comments = new ArrayList<CommentResource>();
    if (post.getComments() != null) {
      for (Comment comment : post.getComments()) {
        this.comments.add(new CommentResource(comment));
      }
    }
  }
}
