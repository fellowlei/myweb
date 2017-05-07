package com.mark.proxy.spring;

import com.mark.proxy.bean.Apology;
import com.mark.proxy.bean.Hello;
import com.mark.proxy.bean.HelloImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/5/7.
 */
public class ClientSpringAop {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring_aop.xml");
        HelloImpl helloImpl = (HelloImpl) context.getBean("helloProxy");
        helloImpl.say("jack");
        Apology apology = (Apology) helloImpl;
        apology.saySorry("jack");
    }
}
