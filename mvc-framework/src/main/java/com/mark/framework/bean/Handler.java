package com.mark.framework.bean;

import java.lang.reflect.Method;

/**
 * ·â×°ActionÐÅÏ¢
 * Created by Administrator on 2017/4/9.
 */
public class Handler {
    private Class<?> controllerClass;
    private Method actionMethod;


    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
