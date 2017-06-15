package com.mark.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet ������
 * Created by lulei on 2017/6/15.
 */
public class ServletHelper {
    private static final Logger logger = LoggerFactory.getLogger(ServletHelper.class);

    /**
     * ʹÿ���̶߳���ӵ��һ�� ServletHelper ʵ��
     */
    private static final ThreadLocal<ServletHelper> servlet_helper_holder = new ThreadLocal<ServletHelper>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    private ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * ��ʼ��
     */
    public static void init(HttpServletRequest request,HttpServletResponse response){
        servlet_helper_holder.set(new ServletHelper(request, response));
    }

    /**
     * ����
     */
    public static void destroy(){
        servlet_helper_holder.remove();
    }

    /**
     * ��ȡ resquest ����
     */
    private static HttpServletRequest getRequest(){
        return servlet_helper_holder.get().request;
    }

    /**
     * ��ȡresponse����
     */
    private static HttpServletResponse getResponse(){
        return servlet_helper_holder.get().response;
    }

    /**
     * ��ȡ Session ����
     * @return
     */
    private static HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * ��ȡ ServletContext ����
     */
    private static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

    /**
     * �����Է��� Request ��
     */
    public static void setRequestAttribute(String key,Object value){
        getRequest().setAttribute(key, value);
    }

    /**
     * �� Request �л�ȡ����
     */
    public static <T> T getRequestAttribute(String key){
        return (T) getRequest().getAttribute(key);
    }

    /**
     * �� Request ���Ƴ�����
     */
    public static void removeRequestAttribute(String key){
        getRequest().removeAttribute(key);
    }

    /**
     * �����ض�����Ӧ
     */
    public static void sendRedirect(String location){
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (IOException e) {
            logger.error("redirect failure",e);
        }
    }

    /**
     * �����Է���Session ��
     */
    public static void setSessionAttribute(String key,Object value){
        getSession().setAttribute(key, value);
    }

    /**
     * �� Session �л�ȡ����
     */
    public static <T> T getSessionAttribute(String key){
        return (T) getRequest().getSession().getAttribute(key);
    }

    /**
     * ��Session ���Ƴ�����
     */
    public static void removeSessionAttribute(String key){
        getRequest().getSession().removeAttribute(key);
    }

    /**
     * ʹ Session ʧЧ
     */
    public static void invalidSession(){
        getRequest().getSession().invalidate();
    }

}
