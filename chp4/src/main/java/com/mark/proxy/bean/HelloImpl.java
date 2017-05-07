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

    public void goodMorning(String name){
        System.out.println("good morning! " + name);
    }

    public void goodNight(String name){
        System.out.println("good night! " + name);
    }


}
