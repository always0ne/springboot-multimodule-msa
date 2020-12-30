package com.restapi.template.errorbot.embedded;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 에러 정보.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {
  /**
   * 사용된 로거.
   */
  @Column(name = "LOGGER_NAME", length = 300)
  private String loggerName;

  /**
   * 에러 메시지.
   */
  @Column(name = "MESSAGE", columnDefinition = "TEXT")
  private String message;

  /**
   * Stack Trace.
   */
  @Column(name = "TRACE", columnDefinition = "TEXT")
  private String trace;

  /**
   * Error 정보.
   *
   * @param eventObject 로그 정보
   */
  public ErrorInfo(ILoggingEvent eventObject) {
    this.loggerName = eventObject.getLoggerName();
    this.message = eventObject.getMessage();
    if (eventObject.getThrowableProxy() != null) {
      this.trace = getStackTrace(eventObject.getThrowableProxy().getStackTraceElementProxyArray());
    }
  }

  /**
   * stackTrace를 String으로 변환.
   *
   * @param stackTraceElements Stack Trace Data
   * @return String으로 변환된 StackTrace 값
   */
  public String getStackTrace(StackTraceElementProxy[] stackTraceElements) {
    if (stackTraceElements == null || stackTraceElements.length == 0) {
      return null;
    }

    StringBuilder stringBuilder = new StringBuilder();
    for (StackTraceElementProxy element : stackTraceElements) {
      stringBuilder.append(element.toString()).append("\n");
    }
    return stringBuilder.toString();
  }
}
