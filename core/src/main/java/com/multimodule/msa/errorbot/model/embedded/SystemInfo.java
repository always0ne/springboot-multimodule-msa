package com.multimodule.msa.errorbot.model.embedded;

import ch.qos.logback.core.util.ContextUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfo {

  @Column(name = "SYSTEM", length = 50)
  private String system;

  @Column(name = "SERVER_NAME", length = 50)
  private String serverName;

  @Column(name = "HOST_NAME", length = 50)
  private String hostName;

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
