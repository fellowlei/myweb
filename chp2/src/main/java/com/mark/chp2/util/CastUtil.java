package com.mark.chp2.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2017/4/8.
 */
public class CastUtil {

    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    private static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    private static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strVal = castString(obj);
            if (StringUtils.isNotEmpty(strVal)) {
                try {
                    doubleValue = Double.parseDouble(strVal);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    private static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strVal = castString(obj);
            if (StringUtils.isNotEmpty(strVal)) {
                try {
                    longValue = Long.parseLong(strVal);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    public static int castIng(Object obj) {
        return CastUtil.castIng(obj, 0);
    }

    private static int castIng(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strVal = castString(obj);
            if (StringUtils.isNotEmpty(strVal)) {
                try {
                    intValue = Integer.parseInt(strVal);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }


    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    private static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }


}
