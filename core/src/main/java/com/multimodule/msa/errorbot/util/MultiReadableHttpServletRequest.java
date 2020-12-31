package com.multimodule.msa.errorbot.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MultiReadableHttpServletRequest extends HttpServletRequestWrapper {

  private ByteArrayOutputStream copiedRequest;

  public MultiReadableHttpServletRequest(HttpServletRequest request) {
    super(request);
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    if (copiedRequest == null) {
      copiedRequest = new ByteArrayOutputStream();
      IOUtils.copy(super.getInputStream(), copiedRequest);
    }
    return new CachedServletInputStream();
  }

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
