package com.multimodule.msa.errorbot.embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RequestInfo {

  @Column(name = "PATH", length = 2048)
  private String path;

  @Column(name = "PARAMETER_MAP", columnDefinition = "TEXT")
  private String parameterMap;

  @Column(name = "HEADER_MAP", columnDefinition = "TEXT")
  private String headerMap;

  @Column(name = "BODY", columnDefinition = "TEXT")
  private String body;

  @Column(name = "AGENT_DETAIL", columnDefinition = "TEXT")
  private String agentDetail;
}
