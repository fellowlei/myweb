package com.mark.framework;

import com.mark.framework.bean.Data;
import com.mark.framework.bean.Handler;
import com.mark.framework.bean.Param;
import com.mark.framework.bean.View;
import com.mark.framework.helper.BeanHelper;
import com.mark.framework.helper.ConfigHelper;
import com.mark.framework.helper.ControllerHelper;
import com.mark.framework.util.CodecUtil;
import com.mark.framework.util.JsonUtil;
import com.mark.framework.util.ReflectionUtil;
import com.mark.framework.util.StreamUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * ����ת����
 * Created by Administrator on 2017/4/9.
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        // ��ʼ�����Helper ��
        HelperLoader.init();
        // ��ȡServletContext ����(����ע�� Servlet)
        ServletContext servletContext = config.getServletContext();
        // ע�ᴦ�� JSP �� Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // ע�ᴦ��̬��Դ��Ĭ�� Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ��ȡ���󷽷�������·��
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        // ��ȡAction ������
        Handler handler = ControllerHelper.getHandler(requestMethod,requestPath);
        if(handler != null){
            // ��ȡController �༰�� Bean ʵ��
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            // ���������������
            Map<String,Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> paramNames = req.getParameterNames();
            while(paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }
            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if(StringUtils.isNoneEmpty(body)){
                String[] params = StringUtils.split(body,"&");
                if(ArrayUtils.isNotEmpty(params)){
                    for(String param: params){
                        String[] array = StringUtils.split(param,"=");
                        if(ArrayUtils.isNotEmpty(array) && array.length == 2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName,paramValue);
                        }
                    }
                }
            }

            Param param = new Param(paramMap);
            // ����Action ����
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
            // ����Action ����ֵ
            if(result instanceof View){
                // ����JSP ҳ��
                View view = (View) result;
                String path = view.getPath();
                if(StringUtils.isNoneEmpty(path)){
                    if(path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath() + path);
                    }else{
                        Map<String,Object> model = view.getModel();
                        for(Map.Entry<String,Object> entry: model.entrySet()){
                            req.setAttribute(entry.getKey(),entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req,resp);
                    }
                }
            }else if(result instanceof Data){
                // ���� JSON ����
                Data data = (Data) result;
                Object model = data.getModel();
                if(model != null){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
