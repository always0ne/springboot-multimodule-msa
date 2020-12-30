package com.restapi.template.errorbot.config;

import ch.qos.logback.classic.LoggerContext;
import com.restapi.template.errorbot.ErrorLogsRepository;
import com.restapi.template.errorbot.ErrorReportAppender;
import com.restapi.template.errorbot.filter.CollectRequestDataFilter;
import com.restapi.template.errorbot.filter.MultiReadableHttpServletRequestFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Logback Appender, Http Request정보 수집필터 등록.
 *
 * @author always0ne
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class LogContextConfig implements InitializingBean {

  private final LogConfig logConfig;
  private final ErrorLogsRepository errorLogsRepository;

  /**
   * LogbackAppender를 등록.
   */
  @Override
  public void afterPropertiesSet() {
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    ErrorReportAppender errorReportAppender =
        new ErrorReportAppender(logConfig, errorLogsRepository);
    errorReportAppender.setContext(loggerContext);
    errorReportAppender.setName("customLogbackAppender");
    errorReportAppender.start();

    loggerContext.getLogger("ROOT").addAppender(errorReportAppender);
  }

  /**
   * Request를 여러번 읽을수 있도록 캐싱하는 필터 등록.
   *
   * @return MultiReadableHttpServletRequestFilter 빈 등록
   */
  @Bean
  public FilterRegistrationBean<MultiReadableHttpServletRequestFilter>
      multiReadableHttpServletRequestFilterRegistrationBean() {
    FilterRegistrationBean<MultiReadableHttpServletRequestFilter> registrationBean =
        new FilterRegistrationBean<>();
    MultiReadableHttpServletRequestFilter multiReadableHttpServletRequestFilter =
        new MultiReadableHttpServletRequestFilter();
    registrationBean.setFilter(multiReadableHttpServletRequestFilter);
    registrationBean.setOrder(1);

    return registrationBean;
  }

  /**
   * Request 정보를 수집하는 필터 등록.
   *
   * @return CollectRequestDataFilter 빈 등록
   */
  @Bean
  public FilterRegistrationBean<CollectRequestDataFilter>
      collectRequestDataFilterRegistrationBean() {
    FilterRegistrationBean<CollectRequestDataFilter> registrationBean =
        new FilterRegistrationBean<>();
    CollectRequestDataFilter collectRequestDataFilter = new CollectRequestDataFilter();
    registrationBean.setFilter(collectRequestDataFilter);
    registrationBean.setOrder(2);

    return registrationBean;
  }
}
