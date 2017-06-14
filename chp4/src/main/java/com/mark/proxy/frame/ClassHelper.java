package com.mark.proxy.frame;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lulei on 2017/6/14.
 */
public class ClassHelper {
    /**
     * 定义类集合(用于存放所加载的类)
     */
    private static final Set<Class<?>> CLASS_SET = new HashSet<Class<?>>();

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包名下某父类（或接口）的所有子类（或实现类）
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for(Class<?> cls: CLASS_SET){
            if(superClass.isAssignableFrom(cls) && ! superClass.equals(cls)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下带有某注解的所有类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for(Class<?> cls: CLASS_SET){
            if(cls.isAnnotationPresent(annotationClass)){
                classSet.add(cls);
            }
        }
        return classSet;
    }


}
