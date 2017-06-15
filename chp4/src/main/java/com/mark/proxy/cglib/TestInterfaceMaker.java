package com.mark.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by lulei on 2017/6/15.
 */
public class TestInterfaceMaker {
    public static void main(String[] args) throws Exception {
        InterfaceMaker interfaceMaker = new InterfaceMaker();
        // 抽取类的方法，生成接口
        interfaceMaker.add(TargetObject.class);
        Class<?> targetIngerface= interfaceMaker.create();
        for(Method method: targetIngerface.getMethods()){
            System.out.println(method.getName());
        }

        // 代理接口
        Object object =  Enhancer.create(Object.class, new Class[]{targetIngerface}, new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                if (method.getName().equals("method1")) {
                    System.out.println("filter method1");
                    return "aaa";
                }
                if (method.getName().equals("method2")) {
                    System.out.println("filter method2");
                    return 222;
                }

                return "default";
            }
        });

        Method method1 = object.getClass().getMethod("method1",new Class[]{String.class});
        System.out.println(method1.invoke(object,new Object[]{"test"}));

        Method method2 = object.getClass().getMethod("method2",new Class[]{int.class});
        System.out.println(method2.invoke(object,new Object[]{1}));

    }
}
