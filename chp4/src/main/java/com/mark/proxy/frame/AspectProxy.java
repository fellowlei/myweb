package com.mark.proxy.frame;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by lulei on 2017/6/14.
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            logger.error("proxy failure", e);
            error(cls, method, params, e);
            throw e;
        } finally {
            end();
        }
        return result;
    }


    public void error(Class<?> cls, Method method, Object[] params, Exception e) {

    }

    public void after(Class<?> cls, Method method, Object[] params, Object result) {

    }


    public void before(Class<?> cls, Method method, Object[] params) {

    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }

    public void begin() {

    }

    public void end() {

    }


}
