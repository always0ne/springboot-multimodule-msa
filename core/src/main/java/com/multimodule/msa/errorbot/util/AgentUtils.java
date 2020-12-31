package com.multimodule.msa.errorbot.util;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class AgentUtils {

  public static Map<String, String> getAgentDetail(HttpServletRequest request) {
    Map<String, String> agentDetail = new HashMap<>();
    UserAgent agent = getUserAgent(request);

    getBrowserInfo(agent, agentDetail);
    getUserOsInfo(agent, agentDetail);

    return agentDetail;
  }

  public static UserAgent getUserAgent(HttpServletRequest request) {
    try {
      return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    } catch (Exception ignored) {
      return null;
    }
  }

  public static void getUserOsInfo(UserAgent userAgent, Map<String, String> agentDetail) {
    OperatingSystem operatingSystem = OperatingSystem.UNKNOWN;
    if (userAgent != null && userAgent.getOperatingSystem() != null) {
      operatingSystem = userAgent.getOperatingSystem();
    }

    agentDetail.put("os", operatingSystem.toString());
    agentDetail.put("deviceType", operatingSystem.getDeviceType().toString());
    agentDetail.put("manufacturer", operatingSystem.getManufacturer().toString());
  }

  public static void getBrowserInfo(UserAgent userAgent, Map<String, String> agentDetail) {
    Browser browser = Browser.UNKNOWN;
    Version version = new Version("0", "0", "0");

    if (userAgent != null && userAgent.getBrowser() != null) {
      browser = userAgent.getBrowser();
      if (userAgent.getBrowserVersion() != null) {
        version = userAgent.getBrowserVersion();
      }
    }
    agentDetail.put("browser", browser.toString());
    agentDetail.put("browserType", browser.getBrowserType().toString());
    agentDetail.put("renderingEngine", browser.getRenderingEngine().toString());
    agentDetail.put("browserVersion", version.toString());
  }
}
