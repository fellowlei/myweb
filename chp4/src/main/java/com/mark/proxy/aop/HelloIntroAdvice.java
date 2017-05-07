package com.mark.proxy.aop;

import com.mark.proxy.bean.Apology;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/5/7.
 */
@Component
public class HelloIntroAdvice extends DelegatingIntroductionInterceptor implements Apology {
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        return super.invoke(mi);
    }


    @Override
    public void saySorry(String name) {
        System.out.println("sorry! " + name);
    }
}
