package com.mark.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet 助手类
 * Created by lulei on 2017/6/15.
 */
public class ServletHelper {
    private static final Logger logger = LoggerFactory.getLogger(ServletHelper.class);

    /**
     * 使每个线程独自拥有一份 ServletHelper 实例
     */
    private static final ThreadLocal<ServletHelper> servlet_helper_holder = new ThreadLocal<ServletHelper>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    private ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 初始化
     */
    public static void init(HttpServletRequest request,HttpServletResponse response){
        servlet_helper_holder.set(new ServletHelper(request, response));
    }

    /**
     * 销毁
     */
    public static void destroy(){
        servlet_helper_holder.remove();
    }

    /**
     * 获取 resquest 对象
     */
    private static HttpServletRequest getRequest(){
        return servlet_helper_holder.get().request;
    }

    /**
     * 获取response对象
     */
    private static HttpServletResponse getResponse(){
        return servlet_helper_holder.get().response;
    }

    /**
     * 获取 Session 对象
     * @return
     */
    private static HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 获取 ServletContext 对象
     */
    private static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

    /**
     * 将属性放入 Request 中
     */
    public static void setRequestAttribute(String key,Object value){
        getRequest().setAttribute(key, value);
    }

    /**
     * 从 Request 中获取属性
     */
    public static <T> T getRequestAttribute(String key){
        return (T) getRequest().getAttribute(key);
    }

    /**
     * 从 Request 中移除属性
     */
    public static void removeRequestAttribute(String key){
        getRequest().removeAttribute(key);
    }

    /**
     * 发送重定向响应
     */
    public static void sendRedirect(String location){
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (IOException e) {
            logger.error("redirect failure",e);
        }
    }

    /**
     * 将属性放入Session 中
     */
    public static void setSessionAttribute(String key,Object value){
        getSession().setAttribute(key, value);
    }

    /**
     * 从 Session 中获取属性
     */
    public static <T> T getSessionAttribute(String key){
        return (T) getRequest().getSession().getAttribute(key);
    }

    /**
     * 从Session 中移除属性
     */
    public static void removeSessionAttribute(String key){
        getRequest().getSession().removeAttribute(key);
    }

    /**
     * 使 Session 失效
     */
    public static void invalidSession(){
        getRequest().getSession().invalidate();
    }

}
