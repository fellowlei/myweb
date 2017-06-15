package com.mark.framework.bean;

/**
 * Created by lulei on 2017/6/15.
 */
public class FormParam {
    private String fileName;
    private Object fieldValue;

    public FormParam(String fileName, Object fieldValue) {
        this.fileName = fileName;
        this.fieldValue = fieldValue;
    }

    public String getFileName() {
        return fileName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
