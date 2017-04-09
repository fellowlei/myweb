package com.mark.framework.helper;

import com.mark.framework.annotation.Inject;
import com.mark.framework.util.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * ����ע��������
 * Created by Administrator on 2017/4/9.
 */
public class IocHelper {
    static {
        // ��ȡ���е�Bean����Bean��ʵ��֮���ӳ���ϵ(��� Bean Map)
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            // ����Bean Map
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                // ��BeanMap�л�ȡBean ����Bean ʵ��
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                // ��ȡBean �ඨ������г�Ա���� (���Bean Field)
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    // ����Bean Field
                    for (Field beanField : beanFields) {
                        // �жϵ�ǰBean Field �Ƿ����Inject ע��
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // ��Bean Map �л�ȡBean Field ��Ӧ��ʵ��
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                // ͨ�������ʼ��BeanField�� ֵ
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
