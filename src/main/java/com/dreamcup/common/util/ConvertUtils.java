package com.dreamcup.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ConvertUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> convertDtoToMap(Object obj) {
        return objectMapper.convertValue(obj, Map.class);
    }

    public static <T> T convertMapToObject(Map<String, Object> map, Class<T> clazz) {
        T object = null;
        try {
            String json = objectMapper.writeValueAsString(map);
            object = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("convertMapToObject {}", e.getLocalizedMessage(), e);
        }
        return object;
    }

    public static <T> String objectToJson(T requestDto) {
        try {
            return objectMapper.writeValueAsString(requestDto);
        } catch (JsonProcessingException e) {
            log.error("objectToJson {}", e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T convertToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("convertToObject {}", e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
