package com.mark.proxy.spring;

import com.mark.proxy.bean.Hello;
import com.mark.proxy.bean.HelloImpl;
import org.springframework.aop.framework.ProxyFactory;

/**
 * Created by Administrator on 2017/5/7.
 */
public class Client {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(); // 创建代理工厂
        proxyFactory.setTarget(new HelloImpl());    // 射入目标类对象
        proxyFactory.addAdvice(new GreetingBeforeAdvice()); // 添加前置增强
        proxyFactory.addAdvice(new GreetingAfterAdvice());  // 添加后置增强
//        proxyFactory.addAdvice(new GreetingBeforeAndAfterAdvice());
//        proxyFactory.addAdvice(new GreetingAroundAdvice()); // 添加环绕增强
        Hello hello = (Hello) proxyFactory.getProxy();  // 从代理工厂中获取代理
        hello.say("jack");
    }
}
