package com.restapi.template.api.community.comment.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restapi.template.api.common.Date;
import com.restapi.template.api.user.data.Users;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 엔터티.
 *
 * @author always0ne
 * @version 1.0
 */
@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "commentId", callSuper = false)
public class Comment extends Date {
  /**
   * pk.
   */
  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId;
  /**
   * 댓글.
   */
  @Column(columnDefinition = "TEXT", nullable = false)
  private String message;

  /**
   * 댓글 작성자.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author")
  private Users author;

  /**
   * 댓글.
   *
   * @param author  댓글 작성자
   * @param message 댓글 본문
   */
  public Comment(Users author, String message) {
    super();
    this.author = author;
    this.message = message;
  }

  /**
   * 댓글 수정.
   *
   * @param message 수정할 메시지
   */
  public void updateComment(String message) {
    this.message = message;
    this.updateModifyDate();
  }
}
