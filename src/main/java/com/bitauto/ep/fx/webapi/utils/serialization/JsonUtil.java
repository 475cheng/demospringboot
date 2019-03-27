package com.bitauto.ep.fx.webapi.utils.serialization;

import com.alibaba.fastjson.JSON;

/**
 * JSON序列化帮助类
 */
public class JsonUtil {

    /**
     * 对象序列化为JSON字符串
     * @param object 对象
     * @return JSON字符串
     */
    public static final String Serialize(Object object){
        return JSON.toJSONString(object);
    }

    /**
     * 反序列化从JSON到对象
     * @param json JSON字符串
     * @param clazz 对象class
     * @param <T> T类型
     * @return T类型对象
     */
    public static <T> T Deserialize(String json,Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

    /**
     * 反序列化从JSON到对象
     * @param json JSON字符串
     * @return object对象
     */
    public static Object Deserialize(String json){
        return JSON.parseObject(json);
    }


}
