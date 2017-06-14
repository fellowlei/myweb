package com.mark.framework;

import com.mark.framework.helper.*;
import com.mark.framework.util.ClassUtil;

/**
 * º”‘ÿ¿‡
 * Created by Administrator on 2017/4/9.
 */
public class HelperLoader {
    public static void init() {
        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, AopHelper.class, IocHelper.class, ControllerHelper.class};
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), false);
        }
    }
}
