package com.mark.proxy;

import com.mark.proxy.bean.Hello;
import com.mark.proxy.bean.HelloImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/4/24.
 */
public class DynamicProxy implements InvocationHandler {
    private Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    private void after() {
        System.out.println("DynamicProxy.after");
    }

    private void before() {
        System.out.println("DynamicProxy.before");
    }

    public static void test1() {
        Hello hello = new HelloImpl();
        DynamicProxy dynamicProxy = new DynamicProxy(hello);

        Hello helloProxy = (Hello) Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), dynamicProxy);
        helloProxy.say("Jack");
    }


    public static void main(String[] args) {
//        test1();
        DynamicProxy dynamicProxy = new DynamicProxy(new HelloImpl());
        Hello helloProxy = dynamicProxy.getProxy();
        helloProxy.say("Jack");
    }
}
