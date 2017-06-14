package com.mark.proxy.frame;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lulei on 2017/6/14.
 */
public class BeanHelper {
    /**
     * 定义Bean 映射 (用于存放Bean 类与Bean 实例的映射关系)
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    /**
     * 设置 Bean 实例
     * @param cls
     * @param obj
     */
    public static void setBean(Class<?> cls,Object obj) {
        BEAN_MAP.put(cls, obj);
    }

    /**
     * 获取Bean 映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取Bean 实例
     */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class: " + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }
}
