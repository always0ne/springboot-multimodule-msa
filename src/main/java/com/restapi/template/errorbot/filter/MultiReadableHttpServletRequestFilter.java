package com.restapi.template.errorbot.filter;

import com.restapi.template.errorbot.util.MultiReadableHttpServletRequest;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Request를 여러번 읽을 수 있도록 복사하는 필터.
 *
 * @author always0ne
 * @version 1.0
 */
public class MultiReadableHttpServletRequestFilter implements Filter {
  /**
   * generate MultiReadable Request.
   *
   * @param req   요청
   * @param res   응답
   * @param chain 체인
   */
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    MultiReadableHttpServletRequest multiReadRequest =
        new MultiReadableHttpServletRequest((HttpServletRequest) req);
    chain.doFilter(multiReadRequest, res);
  }
}
