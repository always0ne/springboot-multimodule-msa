package com.restapi.template.api.community.post.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 게시글 작성,수정 요청.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyPostRequest {
  /**
   * 게시글 제목.
   */
  private String title;
  /**
   * 게시글 본문.
   */
  private String body;
}
