package com.mark.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 流操作工具类
 * Created by lulei on 2017/4/10.
 */
public class StreamUtil {
    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);
    public static String getString(ServletInputStream is) {
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = reader.readLine())!= null){
                sb.append(line);
            }
        }catch (Exception e){
            logger.error("get string failure",3);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
