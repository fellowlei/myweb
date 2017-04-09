package com.mark.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象
 * Created by Administrator on 2017/4/9.
 */
public class View {
    /**
     * 视图路径
     */
    private String path;
    /**
     * 模型数据
     */
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<String, Object>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }
}
