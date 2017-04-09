package com.mark.framework;

import com.mark.framework.helper.BeanHelper;
import com.mark.framework.helper.ClassHelper;
import com.mark.framework.helper.ControllerHelper;
import com.mark.framework.helper.IocHelper;
import com.mark.framework.util.ClassUtil;

/**
 * º”‘ÿ¿‡
 * Created by Administrator on 2017/4/9.
 */
public class HelperLoader {
    public static void init() {
        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, IocHelper.class, ControllerHelper.class};
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), false);
        }
    }
}
