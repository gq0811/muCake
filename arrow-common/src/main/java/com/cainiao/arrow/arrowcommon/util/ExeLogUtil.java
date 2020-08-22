package com.cainiao.arrow.arrowcommon.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExeLogUtil {
    private final static ThreadLocal<List<Object>> LOG_CACHE = new ThreadLocal<>();

    static {
        LOG_CACHE.remove();
    }

    public static void executeAdd(String object) {
        List<Object> logList = getLogList();
        logList.add("【系统日志】" + object);
    }

    public static void add(Object object) {
        List<Object> logList = getLogList();
//        ExecuteContextInfo executeContextInfo = ExecuteContextUtil.getContextInfo().get();
//        if (executeContextInfo != null && !executeContextInfo.getDebugSupport()) {
//            return;
//        }
        logList.add(object);
    }

    public static void addAll(List<Object> addLogList) {
        List<Object> logList = getLogList();
        logList.addAll(addLogList);
    }

    public static List<Object> getLogList() {
        List<Object> logList = LOG_CACHE.get();
        if (logList == null) {
            logList = new LinkedList<>();
            LOG_CACHE.set(logList);
        }
        return logList;
    }

    public static void remove() {
        LOG_CACHE.set(new LinkedList<>());
    }

    private static void reset(List<Object> logList) {
        LOG_CACHE.set(logList);
    }

    public static void resetByCustomMap(String customMapJson) {
        try {
            Map<String, Object> customMap = JSON.parseObject(customMapJson, new TypeReference<Map<String, Object>>() {});
            if (customMap == null) { return; }
            // logList的类型数据已经丢失
            String logListJson = ServiceUtils.getJson(customMap.get("logList"));
            List<Object> logList = JSON.parseObject(logListJson, new TypeReference<List<Object>>() {});
            reset(logList);
        } catch (Throwable e) {
            e.printStackTrace();
            remove();
        }
    }
}