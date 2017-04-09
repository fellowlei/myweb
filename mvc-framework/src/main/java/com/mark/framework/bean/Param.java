package com.mark.framework.bean;

import java.util.Map;

/**
 * Created by Administrator on 2017/4/9.
 */
public class Param {
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public long getLong(String name) {
        return Long.valueOf(paramMap.get(name).toString());
    }
}
