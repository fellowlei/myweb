package com.mark.proxy.spring;

import com.mark.proxy.bean.Hello;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/5/7.
 */
public class ClientSpring {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Hello hello = (Hello) context.getBean("helloProxy");
        hello.say("jack");
    }
}
