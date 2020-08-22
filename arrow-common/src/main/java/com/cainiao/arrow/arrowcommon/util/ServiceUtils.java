package com.cainiao.arrow.arrowcommon.util;

import com.alibaba.fastjson.JSON;

public class ServiceUtils {

    public static String getJson(Object object){
        return JSON.toJSONString(object);
    }
}
