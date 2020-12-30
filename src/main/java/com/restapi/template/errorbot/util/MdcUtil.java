package com.restapi.template.errorbot.util;

import static com.restapi.template.errorbot.util.JsonUtils.toJson;

import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;

/**
 * 장에 발생 시 Request 정보를 가지고 있기 위한 MDC 모듈.
 *
 * @author always0ne
 * @version 1.0
 */
public class MdcUtil {
  private static final MDCAdapter mdc = MDC.getMDCAdapter();

  public static final String HEADER_MAP_MDC = "HEADER_MAP_MDC";

  public static final String PARAMETER_MAP_MDC = "PARAMETER_MAP_MDC";

  public static final String REQUEST_URI_MDC = "REQUEST_URI_MDC";

  public static final String AGENT_DETAIL_MDC = "AGENT_DETAIL_MDC";

  public static final String BODY_MDC = "BODY_MDC";

  public static void putMdc(String key, String value) {
    mdc.put(key, value);
  }

  /**
   * 객체를 Json으로 변환 후 MDC에 추가.
   *
   * @param key   키값
   * @param value 추가할 데이터
   */
  public static void setJsonValueAndPutMdc(String key, Object value) {
    if (value != null) {
      mdc.put(key, toJson(value));
    }
  }

  /**
   * MDC에서 키값으로 데이터 조회.
   *
   * @param key 키값
   * @return key에 매핑된 값
   */
  public static String getFromMdc(String key) {
    return mdc.get(key);
  }

  /**
   * MDC 초기화.
   */
  public static void clear() {
    MDC.clear();
  }
}

