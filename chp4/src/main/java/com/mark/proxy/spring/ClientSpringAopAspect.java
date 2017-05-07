package com.mark.proxy.spring;

import com.mark.proxy.bean.HelloImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/5/7.
 */
public class ClientSpringAopAspect {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_aop_aspect.xml");
        HelloImpl helloImpl = (HelloImpl) context.getBean("helloImpl");
        helloImpl.say("jack");
        helloImpl.goodMorning("jack");
        helloImpl.goodNight("jack");
    }
}
