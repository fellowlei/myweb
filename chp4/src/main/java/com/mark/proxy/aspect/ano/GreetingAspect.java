package com.mark.proxy.aspect.ano;

import com.mark.proxy.bean.Apology;
import com.mark.proxy.bean.ApologyImpl;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/5/7.
 */
@Aspect
@Component
public class GreetingAspect {
    @DeclareParents(value = "com.mark.proxy.bean.HelloImpl",defaultImpl = ApologyImpl.class)
    private Apology apology;
}
