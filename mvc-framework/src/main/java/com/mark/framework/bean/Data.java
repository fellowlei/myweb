package com.mark.framework.bean;

/**
 * 返回数据对象
 * Created by Administrator on 2017/4/9.
 */
public class Data {
    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
