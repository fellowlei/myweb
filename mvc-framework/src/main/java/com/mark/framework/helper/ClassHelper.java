package com.mark.framework.helper;

import com.mark.framework.annotation.Controller;
import com.mark.framework.annotation.Service;
import com.mark.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * �����������
 * Created by Administrator on 2017/4/9.
 */
public class ClassHelper {
    /**
     * �����༯��(���ڴ�������ص���)
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * ��ȡӦ�ð���������Service ��
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * ��ȡӦ�ð���������Controller ��
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * ��ȡӦ�ð���������Bean ��(����: Service,Controller ��)
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }


}
