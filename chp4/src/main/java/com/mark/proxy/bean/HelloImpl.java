package com.mark.proxy.bean;

/**
 * Created by Administrator on 2017/4/24.
 */
public class HelloImpl implements Hello {
    public void say(String name) {
        System.out.println("Hello! " + name);
    }
}
