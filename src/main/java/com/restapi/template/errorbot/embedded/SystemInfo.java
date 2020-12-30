package com.restapi.template.errorbot.embedded;

import ch.qos.logback.core.util.ContextUtil;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 구동중인 서버의 정보.
 *
 * @author always0ne
 * @version 1.0
 */
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfo {

  /**
   * 구동되고 있는 시스템의 OS 정보.
   */
  @Column(name = "SYSTEM", length = 50)
  private String system;

  /**
   * 설정파일에 지정된 서버명.
   */
  @Column(name = "SERVER_NAME", length = 50)
  private String serverName;

  /**
   * 현재 서버의 Host명.
   */
  @Column(name = "HOST_NAME", length = 50)
  private String hostName;

  /**
   * 서버 정보.
   *
   * @param serverName 서버명
   */
  public SystemInfo(String serverName) {
    this.system = System.getProperty("os.name");
    this.serverName = serverName;
    try {
      this.hostName = ContextUtil.getLocalHostName();
    } catch (Exception ignored) {
      this.hostName = "UNKNOWN";
    }
  }
}
