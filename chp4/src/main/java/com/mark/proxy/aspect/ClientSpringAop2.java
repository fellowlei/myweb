package com.mark.proxy.aspect;

import com.mark.proxy.bean.HelloImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/5/7.
 */
public class ClientSpringAop2 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_aop2.xml");
        HelloImpl helloImpl = (HelloImpl) context.getBean("helloProxy");
        helloImpl.say("jack");
        helloImpl.goodMorning("jack");
        helloImpl.goodNight("jack");
    }
}
