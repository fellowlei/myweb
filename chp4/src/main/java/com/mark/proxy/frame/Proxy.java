package com.mark.proxy.frame;

/**
 * Created by lulei on 2017/6/14.
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
