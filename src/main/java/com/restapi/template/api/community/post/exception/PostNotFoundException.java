package com.restapi.template.api.community.post.exception;

/**
 * 없는 게시글 예외
 * 존재하지 않는 게시글입니다.
 *
 * @author always0ne
 * @version 1.0
 */
public class PostNotFoundException extends RuntimeException {
  /**
   * 존재하지 않는 게시글입니다.
   */
  public PostNotFoundException() {
    super("존재하지 않는 게시글입니다.");
  }
}
