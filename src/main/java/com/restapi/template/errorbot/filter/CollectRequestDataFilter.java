package com.restapi.template.errorbot.filter;

import static com.restapi.template.errorbot.util.AgentUtils.getAgentDetail;
import static com.restapi.template.errorbot.util.MdcUtil.AGENT_DETAIL_MDC;
import static com.restapi.template.errorbot.util.MdcUtil.BODY_MDC;
import static com.restapi.template.errorbot.util.MdcUtil.HEADER_MAP_MDC;
import static com.restapi.template.errorbot.util.MdcUtil.PARAMETER_MAP_MDC;
import static com.restapi.template.errorbot.util.MdcUtil.REQUEST_URI_MDC;
import static com.restapi.template.errorbot.util.MdcUtil.clear;
import static com.restapi.template.errorbot.util.MdcUtil.putMdc;
import static com.restapi.template.errorbot.util.MdcUtil.setJsonValueAndPutMdc;

import com.restapi.template.errorbot.util.RequestWrapper;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Request 정보들을 수집하여 MDC에 보관하는 필터.
 *
 * @author always0ne
 * @version 1.0
 */
public class CollectRequestDataFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    RequestWrapper requestWrapper = RequestWrapper.of(request);

    setJsonValueAndPutMdc(HEADER_MAP_MDC, requestWrapper.headerMap());
    setJsonValueAndPutMdc(PARAMETER_MAP_MDC, requestWrapper.parameterMap());
    setJsonValueAndPutMdc(BODY_MDC, requestWrapper.body());
    setJsonValueAndPutMdc(AGENT_DETAIL_MDC, getAgentDetail((HttpServletRequest) request));
    putMdc(REQUEST_URI_MDC, requestWrapper.getRequestUri());

    try {
      chain.doFilter(request, response);
    } finally {
      clear();
    }
  }
}