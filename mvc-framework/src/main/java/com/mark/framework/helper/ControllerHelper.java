package com.mark.framework.helper;

import com.mark.framework.annotation.Action;
import com.mark.framework.bean.Handler;
import com.mark.framework.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ������������
 * Created by Administrator on 2017/4/9.
 */
public class ControllerHelper {
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        // ��ȡ���е�Controller��
        Set<Class<?>> controllerClassSet = com.mark.framework.helper.ClassHelper.getControllerClassSet();

        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            // ������ЩController��
            for (Class<?> controllerClass : controllerClassSet) {
                // ��ȡController���ж���ķ���
                Method[] methods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods)) {
                    // ������ЩController���еķ���
                    for (Method method : methods) {
                        // �жϵ�ǰ�����Ƿ����Actionע��
                        if (method.isAnnotationPresent(Action.class)) {
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            // ��֤ URL ӳ�����
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                    // ��ȡ���󷽷���·��
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, method);
                                    // ��ʼ��Action Map
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * ��ȡHandler
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
