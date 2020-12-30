package com.restapi.template.errorbot.config;

import ch.qos.logback.classic.Level;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 에러로그 수집기 설정.
 *
 * @author always0ne
 * @version 1.0
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "log")
public class LogConfig {

  private String serverName;
  private Level level;
  private Slack slack;
  private Database database;

  @Getter
  @Setter
  public static class Slack {
    private boolean enabled;
    private String webHookUrl;
    private String channel;
    private String userName;
  }

  @Getter
  @Setter
  public static class Database {
    private boolean enabled;
  }
}
