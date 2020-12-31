package com.multimodule.msa.errorbot.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class RequestWrapper {

  private HttpServletRequest request;

  private RequestWrapper(HttpServletRequest request) {
    this.request = request;
  }

  public static RequestWrapper of(HttpServletRequest request) {
    return new RequestWrapper(request);
  }

  public static RequestWrapper of(ServletRequest request) {
    return of((HttpServletRequest) request);
  }

  public Map<String, String> headerMap() {
    Map<String, String> convertedHeaderMap = new HashMap<>();

    Enumeration<String> headerMap = request.getHeaderNames();

    while (headerMap.hasMoreElements()) {
      String name = headerMap.nextElement();
      if (name.equals("user-agent")) {
        continue;
      }
      String value = request.getHeader(name);

      convertedHeaderMap.put(name, value);
    }
    return convertedHeaderMap;
  }

  public Map<String, String> parameterMap() {
    Map<String, String> convertedParameterMap = new HashMap<>();
    Map<String, String[]> parameterMap = request.getParameterMap();

    for (String key : parameterMap.keySet()) {
      String[] values = parameterMap.get(key);
      StringJoiner valueString = new StringJoiner(",");

      for (String value : values) {
        valueString.add(value);
      }

      convertedParameterMap.put(key, valueString.toString());
    }
    return convertedParameterMap;
  }

  public String body() throws IOException {
    String body = null;
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader = null;

    try {
      InputStream inputStream = request.getInputStream();
      if (inputStream != null) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        char[] charBuffer = new char[128];
        int bytesRead = -1;
        while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
          stringBuilder.append(charBuffer, 0, bytesRead);
        }
      } else {
        stringBuilder.append("");
      }
    } catch (IOException ex) {
      throw ex;
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException ex) {
          throw ex;
        }
      }
    }
    body = stringBuilder.toString();
    return body;

  }

  public String getRequestUri() {
    return "[" + request.getMethod() + "] " + request.getRequestURI();
  }
}
