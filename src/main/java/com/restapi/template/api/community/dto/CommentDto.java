package com.restapi.template.api.community.dto;

import com.restapi.template.api.community.comment.data.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDto {
  private Long commentId;
  private String message;
  private String commenterId;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

  /**
   * Comment to Dto.
   *
   * @param comment 댓글
   */
  public CommentDto(Comment comment) {
    this.commentId = comment.getCommentId();
    this.message = comment.getMessage();
    this.commenterId = comment.getAuthor().getUserId();
    this.createdDate = comment.getCreatedDate();
    this.modifiedDate = comment.getModifiedDate();
  }
}
