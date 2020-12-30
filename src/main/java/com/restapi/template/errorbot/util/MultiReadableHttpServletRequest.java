package com.restapi.template.errorbot.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;

/**
 * Request를 여러번 읽을 수 있도록 복사한 객체.
 *
 * @author always0ne
 * @version 1.0
 */
public class MultiReadableHttpServletRequest extends HttpServletRequestWrapper {

  private ByteArrayOutputStream copiedRequest;

  /**
   * Constructs a request object wrapping the given request.
   *
   * @param request The request to wrap
   * @throws IllegalArgumentException if the request is null
   */
  public MultiReadableHttpServletRequest(HttpServletRequest request) {
    super(request);
  }

  /**
   * 복사된 Input Stream을 반환.
   * 값이 복사되어있지 않은 경우는 처음호출된 경우이므로 Stream을 복사
   */
  @Override
  public ServletInputStream getInputStream() throws IOException {
    if (copiedRequest == null) {
      copiedRequest = new ByteArrayOutputStream();
      IOUtils.copy(super.getInputStream(), copiedRequest);
    }
    return new CachedServletInputStream();
  }

  /**
   * 복사된 Stream을 ServletInputStream으로 변환.
   */
  public class CachedServletInputStream extends ServletInputStream {
    private ByteArrayInputStream input;

    public CachedServletInputStream() {
      input = new ByteArrayInputStream(copiedRequest.toByteArray());
    }

    @Override
    public boolean isFinished() {
      return false;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
    }

    @Override
    public int read() {
      return input.read();
    }
  }
}
