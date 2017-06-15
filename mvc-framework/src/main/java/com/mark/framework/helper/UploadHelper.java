package com.mark.framework.helper;

import com.mark.framework.bean.FileParam;
import com.mark.framework.bean.FormParam;
import com.mark.framework.bean.Param;
import com.mark.framework.util.FileUtil;
import com.mark.framework.util.StreamUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 文件上传助手类
 * Created by lulei on 2017/6/15.
 */
public class UploadHelper {
    private static final Logger logger = LoggerFactory.getLogger(UploadHelper.class);

    /**
     * Apache commons FileUpload 提供的 Servlet 文件上传对象
     */
    private static ServletFileUpload servletFileUpload;

    public static void init(ServletContext servletContext){
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,repository));
        int uploadLimit = 100;
        servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
    }

    public static boolean isMultipart(HttpServletRequest request){
        return ServletFileUpload.isMultipartContent(request);
    }

    public static Param createParam(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        List<FileParam> fileParamList = new ArrayList<FileParam>();

        try{
            Map<String,List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if(MapUtils.isNotEmpty(fileItemListMap)){
                for(Map.Entry<String,List<FileItem>> fileItemListEntry: fileItemListMap.entrySet()){
                    String fieldName = fileItemListEntry.getKey();
                    List<FileItem> fileItemList = fileItemListEntry.getValue();
                    if(CollectionUtils.isNotEmpty(fileItemList)){
                        for(FileItem fileItem: fileItemList){
                            if(fileItem.isFormField()){
                                String fieldValue = fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(fieldName,fieldValue));
                            }else{
                                String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                                if(StringUtils.isNoneEmpty(fileName)){
                                    long fileSize = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName,fileName,fileSize,contentType,inputStream));
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error("create param failure",e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList,fileParamList);
    }

    public static void uploadFile(String basePath,FileParam fileParam){
        try{
            if(fileParam != null){
                String filePath = basePath + fileParam.getFileName();
                FileUtil.createFile(filePath);
                InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            }
        }catch (Exception e){
            logger.error("upload file failure",e);
            throw new RuntimeException(e);
        }
    }

    public static void uploadFile(String basePath,List<FileParam> fileParamList){
        try{
            if(CollectionUtils.isNotEmpty(fileParamList)){
                for(FileParam fileParam: fileParamList){
                    uploadFile(basePath, fileParam);
                }
            }
        }catch (Exception e){
            logger.error("upload file failure",e);
            throw new RuntimeException(e);
        }
    }
}
