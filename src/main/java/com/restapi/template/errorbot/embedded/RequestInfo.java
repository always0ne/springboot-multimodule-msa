package com.restapi.template.errorbot.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request 정보.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RequestInfo {

  /**
   * 요청된 메스드, URL.
   */
  @Column(name = "PATH", length = 2048)
  private String path;

  /**
   * Parameter.
   */
  @Column(name = "PARAMETER_MAP", columnDefinition = "TEXT")
  private String parameterMap;

  /**
   * Header.
   */
  @Column(name = "HEADER_MAP", columnDefinition = "TEXT")
  private String headerMap;

  /**
   * Body.
   */
  @Column(name = "BODY", columnDefinition = "TEXT")
  private String body;

  /**
   * Agent 정보.
   */
  @Column(name = "AGENT_DETAIL", columnDefinition = "TEXT")
  private String agentDetail;
}
