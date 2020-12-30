package com.restapi.template.errorbot.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Json 변환에 사용되는 모듈.
 *
 * @author always0ne
 * @version 1.0
 */
public class JsonUtils {
  private final ObjectMapper mapper;

  private JsonUtils() {
    mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, true);
    mapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, true);
    mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
  }

  public static JsonUtils getInstance() {
    return new JsonUtils();
  }

  private static ObjectMapper getMapper() {
    return getInstance().mapper;
  }

  /**
   * 객체를 Json으로 변환.
   *
   * @param object Json으로 변환할 객체
   * @return Json화 된 객체
   */
  public static String toJson(Object object) {
    try {
      return getMapper().writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Json을 객체로 변환.
   *
   * @param jsonStr Json 문자열
   * @param cls     변환할 객체
   * @return Json에서 추출한 객체
   */
  public static <T> T fromJson(String jsonStr, Class<T> cls) {
    try {
      return getMapper().readValue(jsonStr, cls);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Json을 보기 좋게 변환.
   *
   * @param json Json 문자열
   * @return 가독성이 좋아진 Json
   */
  public static String toPrettyJson(String json) {
    Object jsonObject = JsonUtils.fromJson(json, Object.class);
    try {
      return getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }
}

