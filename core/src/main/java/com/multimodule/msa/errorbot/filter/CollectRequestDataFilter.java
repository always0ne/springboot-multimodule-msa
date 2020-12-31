package com.multimodule.msa.errorbot.filter;

import com.multimodule.msa.errorbot.util.MdcUtil;
import com.multimodule.msa.errorbot.util.RequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.multimodule.msa.errorbot.util.AgentUtils.getAgentDetail;
import static com.multimodule.msa.errorbot.util.MdcUtil.*;

public class CollectRequestDataFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    RequestWrapper requestWrapper = RequestWrapper.of(request);

    setJsonValueAndPutMdc(HEADER_MAP_MDC, requestWrapper.headerMap());
    setJsonValueAndPutMdc(PARAMETER_MAP_MDC, requestWrapper.parameterMap());
    setJsonValueAndPutMdc(BODY_MDC, requestWrapper.body());
    setJsonValueAndPutMdc(MdcUtil.AGENT_DETAIL_MDC, getAgentDetail((HttpServletRequest) request));
    putMdc(REQUEST_URI_MDC, requestWrapper.getRequestUri());

    try {
      chain.doFilter(request, response);
    } finally {
      clear();
    }
  }
}