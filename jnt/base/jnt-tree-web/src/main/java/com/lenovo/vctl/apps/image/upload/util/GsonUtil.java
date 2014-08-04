package com.lenovo.vctl.apps.image.upload.util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {

    private static Gson gson = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .disableHtmlEscaping() .create();

    public static <T> T fromJson(String json, Class<T> clasz) {
//		Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, clasz);
    }

    public static <T> T fromJson(String json, Type type) {
        return (T) gson.fromJson(json, type);
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static String toJson(Object src, Type srcType) {
        return gson.toJson(src, srcType);
    }

    public static String toJsonWithoutNull(Object src, Type srcType) {
        Gson gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .disableHtmlEscaping()
             .create();

        return gson.toJson(src, srcType);
    }

    public static Object get(Object key, String snapshot) {
        if (null == key || StringUtils.isBlank(snapshot)) {
            return null;
        }
        Map<Object, Object> map = null;
        try {
            map = gson.fromJson(snapshot, new TypeToken<Map<Object, Object>>() { }.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        return map.get(key);
    }

    public static String put(String snapshot, Object key, Object value) {
        Map<Object, Object> map = gson.fromJson(snapshot, new TypeToken<Map<Object, Object>>() {}.getType());
        map.put(key, value);
        return gson.toJson(map);
    }

    /**
     * 目前只能判断一层
     * @param str
     * @return
     */
    public static boolean maybeJson(String str) {
        if (StringUtils.isEmpty(str)) return false;

        //先判断是否带{},减少Exception次数
        if (StringUtils.containsNone(str, "{") || StringUtils.containsNone(str, "}")) {
            return false;
        }

        try {
            gson.fromJson(str, new TypeToken<Map<Object, Object>>(){}.getType());
            return true;
        } catch (Exception ex){
            return false;
        }
    }

    /**
     * using jackson to parse json string,
     * gson no good, because of converting all number to double, eg. 1 => 1.0
     * @param json
     * @return
     */
    public static Map<String, Object> convertJsonToMap(String json) {
        Map<String, Object> map = new HashMap<String, Object>();

        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(json, new TypeReference<HashMap<String,Object>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

}
