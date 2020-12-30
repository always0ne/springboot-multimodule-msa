package com.restapi.template.errorbot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Request 정보를 보기 쉽게 가공.
 *
 * @author always0ne
 * @version 1.0
 */
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

  /**
   * user-agent정보를 제외한 헤더정보를 매핑하여 반환.
   *
   * @return 매핑된 헤더 정보
   */
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

  /**
   * 요청의 Parameter를 매핑하여 반환.
   *
   * @return 매핑된 Parameter 정보
   */
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

  /**
   * 요청의 Body를 반환.
   *
   * @return Request Body
   * @throws IOException 문자열을 읽는 도중 나올수 있는 예외
   */
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

  /**
   * 요청 URL과 메소드를 반환.
   *
   * @return [Method] URL
   */
  public String getRequestUri() {
    return "[" + request.getMethod() + "] " + request.getRequestURI();
  }
}
