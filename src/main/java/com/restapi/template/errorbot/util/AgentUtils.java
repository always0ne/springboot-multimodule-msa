package com.restapi.template.errorbot.util;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Agent정보를 추출하는 모듈.
 *
 * @author always0ne
 * @version 1.0
 */
public class AgentUtils {

  /**
   * UserAgent 정보 매핑.
   *
   * @param request 요청 정보
   * @return 매핑된 User Agent 정보
   */
  public static Map<String, String> getAgentDetail(HttpServletRequest request) {
    Map<String, String> agentDetail = new HashMap<>();
    UserAgent agent = getUserAgent(request);

    getBrowserInfo(agent, agentDetail);
    getUserOsInfo(agent, agentDetail);

    return agentDetail;
  }

  /**
   * 헤더에서 User-Agent 정보 추출.
   *
   * @param request 요청 정보
   * @return User Agent 정보
   */
  public static UserAgent getUserAgent(HttpServletRequest request) {
    try {
      return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    } catch (Exception ignored) {
      return null;
    }
  }

  /**
   * 헤더에서 접속자의 OS 정보 추출.
   *
   * @param userAgent   사용자 정보
   * @param agentDetail UserAgent 를 매핑할 객체
   */
  public static void getUserOsInfo(UserAgent userAgent, Map<String, String> agentDetail) {
    OperatingSystem operatingSystem = OperatingSystem.UNKNOWN;
    if (userAgent != null && userAgent.getOperatingSystem() != null) {
      operatingSystem = userAgent.getOperatingSystem();
    }

    agentDetail.put("os", operatingSystem.toString());
    agentDetail.put("deviceType", operatingSystem.getDeviceType().toString());
    agentDetail.put("manufacturer", operatingSystem.getManufacturer().toString());
  }

  /**
   * 헤더에서 접속자 브라우저 정보 추출.
   *
   * @param userAgent   사용자 정보
   * @param agentDetail UserAgent 를 매핑할 객체
   */
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
