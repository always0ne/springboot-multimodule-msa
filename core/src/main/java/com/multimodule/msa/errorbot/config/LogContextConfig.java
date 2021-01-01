package com.multimodule.msa.errorbot.config;

import ch.qos.logback.classic.LoggerContext;
import com.multimodule.msa.errorbot.repository.ErrorLogsRepository;
import com.multimodule.msa.errorbot.controller.ErrorReportAppender;
import com.multimodule.msa.errorbot.filter.CollectRequestDataFilter;
import com.multimodule.msa.errorbot.filter.MultiReadableHttpServletRequestFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LogContextConfig implements InitializingBean {

  private final LogConfig logConfig;
  private final ErrorLogsRepository errorLogsRepository;

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
