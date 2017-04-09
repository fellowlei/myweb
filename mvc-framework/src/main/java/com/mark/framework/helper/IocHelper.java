package com.mark.framework.helper;

import com.mark.framework.annotation.Inject;
import com.mark.framework.util.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 * Created by Administrator on 2017/4/9.
 */
public class IocHelper {
    static {
        // 获取所有的Bean类与Bean类实例之间的映射关系(简称 Bean Map)
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            // 遍历Bean Map
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                // 从BeanMap中获取Bean 类与Bean 实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                // 获取Bean 类定义的所有成员遍历 (简称Bean Field)
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    // 遍历Bean Field
                    for (Field beanField : beanFields) {
                        // 判断当前Bean Field 是否带有Inject 注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // 在Bean Map 中获取Bean Field 对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                // 通过反射初始化BeanField的 值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
