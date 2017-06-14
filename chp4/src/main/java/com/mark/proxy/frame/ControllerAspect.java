package com.mark.proxy.frame;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;


/**
 * Created by lulei on 2017/6/14.
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {
    private static  final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) {
        logger.debug("--------begin----------");
        logger.debug(String.format("calss: %s",cls.getName()));
        logger.debug(String.format("method: %s",method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) {
        logger.debug(String.format("time: %dms"),System.currentTimeMillis() -begin);
        logger.debug("---------end-------------");
    }
}
