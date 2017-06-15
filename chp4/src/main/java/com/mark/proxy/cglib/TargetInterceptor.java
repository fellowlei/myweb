package com.mark.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by lulei on 2017/6/15.
 */
public class TargetInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
        System.out.println("before");
        Object result = methodProxy.invokeSuper(obj, params);
        System.out.println("after");
        return result;
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new TargetInterceptor());
        TargetObject obj2 = (TargetObject) enhancer.create();
        System.out.println(obj2.method1("mark"));
        System.out.println(obj2.method2(1));
    }
}
