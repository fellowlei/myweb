package com.mark.proxy.cglib;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.proxy.*;

import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * Created by lulei on 2017/6/15.
 */
public class TargetObject {
    public String method1(String name){
        return name;
    }

    public  int method2(int count){
        return count;
    }

    public static void main(String[] args) {
        Enhancer enhancer =  new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                if(method.getName().equals("method1")){
                    System.out.println("filter method1 ==0");
                    return 0;
                }
                if(method.getName().equals("method2")){
                    System.out.println("filter method2 ==1");
                    return 0;
                }
                return 0;
            }
        });
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("before");
                Object result = methodProxy.invokeSuper(obj,args);
                System.out.println("after");

                return result;
            }
        });
//        enhancer.setCallback(new FixedValue() {
//            @Override
//            public Object loadObject() throws Exception {
//                return "testvv";
//            }
//        });

        TargetObject targetObject = (TargetObject) enhancer.create();
        System.out.println(targetObject.method1("test"));
//        System.out.println(targetObject.method2(1));

    }


}
