package com.restapi.template.api.common.response;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

/**
 * 링크만 제공되는 응답.
 *
 * @author always0ne
 * @version 1.0
 */
public class LinksResponse extends RepresentationModel {
  /**
   * Body는 없고 Links만 있을 때.
   *
   * @param links 제공되는 링크들
   */
  public LinksResponse(Link... links) {
    this.add(links);
  }
}
