package com.mark.proxy.bean;

/**
 * Created by Administrator on 2017/5/7.
 */
public class ApologyImpl implements Apology{
    @Override
    public void saySorry(String name) {
        System.out.println("sorry! " + name);
    }
}
