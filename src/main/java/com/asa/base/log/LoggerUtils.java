package com.asa.base.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by andrew_asa on 2017/8/17.
 */
public class LoggerUtils {

    private static Map<Class, Logger> loggerMap = new ConcurrentHashMap();

    public static Logger getLogger(Class clazz) {

        if (!loggerMap.containsKey(clazz)) {
            loggerMap.put(clazz, LoggerFactory.getLogger(clazz));
        }
        return loggerMap.get(clazz);
    }

    public static Logger getLogger() {

        return getLogger(LoggerUtils.class);
    }
}
