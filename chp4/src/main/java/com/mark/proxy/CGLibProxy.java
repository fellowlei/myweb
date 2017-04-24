package com.mark.proxy;

import com.mark.proxy.bean.Hello;
import com.mark.proxy.bean.HelloImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/4/24.
 */
public class CGLibProxy implements MethodInterceptor {
    private static CGLibProxy instance = new CGLibProxy();

    private CGLibProxy() {
    }

    public static CGLibProxy getInstance() {
        return instance;
    }

    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        Object result = proxy.invokeSuper(obj, args);
        after();
        return result;
    }

    private void after() {
        System.out.println("CGLibProxy.after");
    }

    private void before() {
        System.out.println("CGLibProxy.before");
    }

    public static void test1() {
        CGLibProxy cgLibProxy = new CGLibProxy();
        Hello helloProxy = cgLibProxy.getProxy(HelloImpl.class);
        helloProxy.say("Jack");
    }

    public static void main(String[] args) {
//        test1();
        Hello helloProxy = CGLibProxy.getInstance().getProxy(HelloImpl.class);
        helloProxy.say("Jack");
    }
}
