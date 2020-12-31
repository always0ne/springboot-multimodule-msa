package com.multimodule.msa.errorbot.embedded;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo {

  @Column(name = "LOGGER_NAME", length = 300)
  private String loggerName;

  @Column(name = "MESSAGE", columnDefinition = "TEXT")
  private String message;

  @Column(name = "TRACE", columnDefinition = "TEXT")
  private String trace;

  public ErrorInfo(ILoggingEvent eventObject) {
    this.loggerName = eventObject.getLoggerName();
    this.message = eventObject.getMessage();
    if (eventObject.getThrowableProxy() != null) {
      this.trace = getStackTrace(eventObject.getThrowableProxy().getStackTraceElementProxyArray());
    }
  }

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
