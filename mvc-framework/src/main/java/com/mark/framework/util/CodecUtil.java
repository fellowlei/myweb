package com.mark.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by lulei on 2017/4/10.
 */
public class CodecUtil {
    private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);

    /**
     * ½« URL ±àÂë
     */
    public static String encodeURL(String source){
        String target;
        try{
            target = URLEncoder.encode(source,"UTF-8");
        }catch (Exception e){
            logger.error("encode url failure",e);
            throw new RuntimeException(e);
        }
        return target;
    }
    /**
     * ½« URL ½âÂë
     */
    public static String decodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("decode url failure",e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
