package com.cainiao.arrow.arrowcommon.util;

import com.cainiao.arrow.arrowcommon.dto.CacheObject;
import com.cainiao.arrow.arrowcommon.inter.CacheDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存
 */
public class LocalCacheUtil {
    private static Logger logger = LoggerFactory.getLogger(LocalCacheUtil.class.getName());

    private static Map<String, CacheObject> memoCache = new ConcurrentHashMap<>();

    public static <T> T get(String key) {
        if (!memoCache.containsKey(key)) {
            return null;
        } else {
            CacheObject cacheObject = memoCache.get(key);
            if (cacheObject.isExpired() || null == cacheObject.getValue()) {
                try {
                    Object value = cacheObject.getGenerator().generate();
                    cacheObject.setValue(value);
                    return (T)value;
                } catch (Throwable e) {
                    logger.error("key = " + key, e);
                    throw e;
                }
            }
            return (T)cacheObject.getValue();
        }
    }

    public static void put(String key, Object value, Long duration) {
        if (memoCache.containsKey(key)) {
            CacheObject cacheObject = memoCache.get(key);
            cacheObject.setDuration(duration);
            cacheObject.setValue(value);
        }
    }

    public static void forcePut(String key, Object value, Long duration) {
        CacheObject cacheObject = new CacheObject();
        cacheObject.setValue(value);
        cacheObject.setDuration(duration);
        cacheObject.setStartTime(System.currentTimeMillis());
        memoCache.put(key, cacheObject);
    }

    public static <T> T get(String key, Long duration, CacheDataGenerator<T> generator) {
        try {
            CacheObject cacheObject = new CacheObject();
            if (memoCache.containsKey(key)) {
                cacheObject = memoCache.get(key);
                if (null != cacheObject.getValue() && !cacheObject.isExpired()) {
                    return (T)cacheObject.getValue();
                }
            }

            T value = generator.generate();
            if (value == null) {
                throw new RuntimeException("通过处理器获取的value为空");
            }
            cacheObject.setValue(value);
            cacheObject.setDuration(duration);
            cacheObject.setStartTime(System.currentTimeMillis());
            cacheObject.setGenerator(generator);
            memoCache.put(key, cacheObject);
            clearExpired();
            return value;
        } catch (Throwable e) {
            logger.error("key = " + key, e);
            throw e;
        }
    }

    public static void clearExpired() {
        ThreadUtil.smallCommonExecutor.submit(() -> {
            Iterator<Map.Entry<String, CacheObject>> iterator = memoCache.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, CacheObject> entry = iterator.next();
                CacheObject cacheObject = entry.getValue();
                if (cacheObject == null || cacheObject.getValue() == null || cacheObject.isExpired()) {
                    iterator.remove();
                }
            }
        });
    }

    public static void clear(String key) {
        memoCache.remove(key);
    }

}
