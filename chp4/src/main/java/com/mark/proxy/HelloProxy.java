package com.mark.proxy;

import com.mark.proxy.bean.Hello;
import com.mark.proxy.bean.HelloImpl;

/**
 * Created by Administrator on 2017/4/24.
 */
public class HelloProxy implements Hello {
    private Hello hello;
    public HelloProxy(){
        hello = new HelloImpl();
    }
    public void say(String name) {
        before();
        hello.say(name);
        after();
    }

    private void after() {
        System.out.println("HelloProxy.after");
    }

    private void before() {
        System.out.println("HelloProxy.before");
    }

    public static void main(String[] args) {
        Hello helloProxy = new HelloProxy();
        helloProxy.say("Jack");
    }


}
