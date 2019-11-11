package com.zzw.util;

public final class CommonUtil {

    /**
     * 产生编号
     */
    public static String randSn(String sign) {
        return String.valueOf(sign + System.currentTimeMillis() + (int) (Math.random() * 100000));
    }
}
