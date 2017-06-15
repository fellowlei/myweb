package com.mark.framework;

import com.mark.framework.bean.Data;
import com.mark.framework.bean.Handler;
import com.mark.framework.bean.Param;
import com.mark.framework.bean.View;
import com.mark.framework.helper.*;
import com.mark.framework.util.JsonUtil;
import com.mark.framework.util.ReflectionUtil;
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
import java.util.Map;

/**
 * 请求转发器
 * Created by Administrator on 2017/4/9.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        // 初始化相关Helper 类
        HelperLoader.init();
        // 获取ServletContext 对象(用于注册 Servlet)
        ServletContext servletContext = config.getServletContext();
        // 注册处理 JSP 的 Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 注册处理静态资源的默认 Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletHelper.init(req, resp);
        try {
            // 获取请求方法与请求路径
            String requestMethod = req.getMethod().toLowerCase();
            String requestPath = req.getPathInfo();

            if (requestPath.equals("/favicon.ico")) {
                return;
            }
            // 获取Action 处理器
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if (handler != null) {
                // 获取Controller 类及其 Bean 实例
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);

                // 创建请求参数对象
                Param param;
                if (UploadHelper.isMultipart(req)) {
                    param = UploadHelper.createParam(req);
                } else {
                    param = RequestHelper.createParam(req);
                }

                // 调用Action 方法
                Method actionMethod = handler.getActionMethod();
                Object result;
                if (param.isEmpty()) {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                } else {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }

                // 处理Action 返回值
                if (result instanceof View) {
                    // 返回JSP 页面
                    handleViewResult((View) result, req, resp);
                } else if (result instanceof Data) {
                    // 返回 JSON 数据
                    handleDataResult((Data) result, resp);
                }
            }
        } finally {
            ServletHelper.destroy();
        }
    }

    private void handleDataResult(Data data, HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    private void handleViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtils.isNoneEmpty(path)) {
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
            }
        }
    }
}
