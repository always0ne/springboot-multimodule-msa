package com.restapi.template.api.community.post.data;

import com.restapi.template.api.common.Date;
import com.restapi.template.api.community.comment.data.Comment;
import com.restapi.template.api.user.data.Users;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시글 엔터티.
 *
 * @author always0ne
 * @version 1.0
 */
@Entity
@NoArgsConstructor
@Getter
public class Post extends Date {
  /**
   * pk.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;
  /**
   * 글 제목.
   */
  @Column(length = 100, nullable = false)
  private String title;
  /**
   * 본문.
   */
  @Column(columnDefinition = "TEXT", nullable = false)
  private String body;
  /**
   * 조회수.
   */
  @Column(nullable = false)
  private Long views;
  /**
   * 댓글수.
   */
  @Column(nullable = false)
  private Long commentNum;
  /**
   * 작성자.
   */
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "author")
  private Users author;
  /**
   * 댓글들.
   *
   * @see Comment
   */
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "post_id")
  private List<Comment> comments;

  /**
   * 게시글.
   *
   * @param id     게시글  ID
   * @param author 작성자
   * @param title  제목
   * @param body   본문
   */
  public Post(Long id, Users author, String title, String body) {
    super();
    this.postId = id;
    this.author = author;
    this.title = title;
    this.body = body;
    this.views = (long) 0;
    this.commentNum = (long) 0;
  }

  /**
   * 조회수 증가.
   */
  public void increaseViews() {
    this.views++;
  }

  /**
   * 게시글 수정.
   * 데이터 변경
   *
   * @param title 글 제목
   * @param body  글 본문
   */
  public void updatePost(String title, String body) {
    this.title = title;
    this.body = body;
    this.updateModifyDate();
  }

  /**
   * 댓글 추가.
   *
   * @param comment 댓글
   */
  public void addComment(Comment comment) {
    if (this.comments == null) {
      this.comments = new ArrayList<Comment>();
    }
    this.comments.add(comment);
    this.commentNum++;
  }

  /**
   * 댓글 수정.
   *
   * @param comment 댓글
   * @param message 수정할 메시지
   */
  public void updateComment(Comment comment, String message) {
    comment.updateComment(message);
    this.comments.set(this.comments.indexOf(comment), comment);
  }

  /**
   * 댓글 삭제.
   *
   * @param comment 댓글
   */
  public void deleteComment(Comment comment) {
    this.comments.remove(comment);
    this.commentNum--;
  }
}
