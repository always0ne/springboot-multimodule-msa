package com.multimodule.msa.errorbot.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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

  public static String toJson(Object object) {
    try {
      return getMapper().writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJson(String jsonStr, Class<T> cls) {
    try {
      return getMapper().readValue(jsonStr, cls);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

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

