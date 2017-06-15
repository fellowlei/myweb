package com.mark.framework.bean;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/9.
 */
public class Param {
    private List<FormParam> formParamList;
    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获取请求参数映射
     */
    public Map<String,Object> getFieldMap(){
        Map<String,Object> fieldMap = new HashMap<String, Object>();
        if(CollectionUtils.isNotEmpty(formParamList)){
            for(FormParam formParam: formParamList){
                String fieldName = formParam.getFileName();
                Object fieldValue = formParam.getFieldValue();
                if(fieldMap.containsKey(fieldName)){
                    fieldValue = fieldMap.get(fieldName) + ":" + fieldValue;
                }
                fieldMap.put(fieldName,fieldValue);
            }
        }

        return fieldMap;
    }

    /**
     * 获取上传文件映射
     */
    public Map<String,List<FileParam>> getFileMap(){
        Map<String,List<FileParam>> fileMap = new HashMap<String, List<FileParam>>();
        if(CollectionUtils.isNotEmpty(fileParamList)){
            for(FileParam fileParam: fileParamList){
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParamList;
                if(fileMap.containsKey(fieldName)){
                    fileParamList = fileMap.get(fieldName);
                }else{
                    fileParamList = new ArrayList<FileParam>();
                }
                fileParamList.add(fileParam);
                fileMap.put(fieldName,fileParamList);
            }
        }
        return fileMap;
    }

    public List<FileParam> getFileList(String fileNmae){
        return getFileMap().get(fileNmae);
    }

    public FileParam getFile(String fileName){
        List<FileParam> fileParamList = getFileList(fileName);
        if(CollectionUtils.isNotEmpty(fileParamList) && fileParamList.size() ==1){
            return fileParamList.get(0);
        }
        return null;
    }

    public boolean isEmpty(){
        return CollectionUtils.isEmpty(formParamList) && CollectionUtils.isEmpty(fileParamList);
    }

    public String getString(String name){
        return String.valueOf(getFieldMap().get(name));
    }






}
