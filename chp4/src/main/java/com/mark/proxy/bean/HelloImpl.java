package com.mark.proxy.bean;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/4/24.
 */
@Component
public class HelloImpl implements Hello {
    public void say(String name) {
        System.out.println("Hello! " + name);
    }
}
