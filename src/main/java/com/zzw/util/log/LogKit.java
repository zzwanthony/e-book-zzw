package com.zzw.util.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class LogKit {
    private static Log log = LogFactory.getLog("nix");
    private static String getClassName(Class clazz){
        return clazz.getName() + " : ";
    }
    public static void info(Class clazz,String msg){
        log.info(getClassName(clazz) + msg);
    }

    public static void info(String msg) {
        log.info(msg);
    }

    public static void debug(Class clazz,String msg){
        log.debug(getClassName(clazz) + msg);
    }
    public static void debug(String msg){
        log.debug(msg);
    }

    public static void error(Class clazz,String msg){
        log.error(getClassName(clazz) + msg);
    }

    public static void error(String msg){
        log.error(msg);
    }
}